import { Component, OnInit } from '@angular/core';
import { ConfirmationService, MenuItem, MessageService } from 'primeng/api';
import { PermissionEnum } from 'src/app/core/constants/permisions';
import { ColorCService } from 'src/app/core/http/catalogs/color-c/color-c.service';
import { PermissionService } from 'src/app/core/http/permissions/permission.service';
import { BreadcrumbService } from 'src/app/core/services/breadcrumb.service';
import { TypePermission } from 'src/app/types/permission';
import { ColorCatalog } from 'src/app/types/color-catalog.types';

@Component({
  selector: 'app-color-c',
  templateUrl: './color-c.component.html',
  styleUrls: ['./color-c.component.scss'],
  styles: [
    `
        :host ::ng-deep .p-dialog .product-image {
            width: 150px;
            margin: 0 auto 2rem auto;
            display: block;
        }

        @media screen and (max-width: 960px) {
            :host
            ::ng-deep
            .p-datatable.p-datatable-customers
            .p-datatable-tbody
            > tr
            > td:last-child {
                text-align: center;
            }

            :host
            ::ng-deep
            .p-datatable.p-datatable-customers
            .p-datatable-tbody
            > tr
            > td:nth-child(6) {
                display: flex;
            }
        }
    `,
  ],
  providers: [MessageService, ConfirmationService],
})
export class ColorCComponent implements OnInit {

  colorDialog: boolean;

  selectedColor: ColorCatalog[];

  submitted: boolean;

  cols: any[];

  colors: ColorCatalog[];

  color: ColorCatalog;

  loading = true;

  isEdit: boolean;

  permissionsPage: TypePermission[];

  items: MenuItem[] = [];

  colorSelect: ColorCatalog;

  constructor(
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private breadcrumbService: BreadcrumbService,
    private colorCService: ColorCService,
    private permissionService: PermissionService,
  ) {
    this.breadcrumbService.setItems([
      { label: 'Diseño' },
      { label: 'Catálogos' },
      { label: 'Colores C', routerLink: ['home/catalogs/colores-c'] },
    ]);
  }

  ngOnInit() {
    this.getPermissionsPage();
    this.getAll();
    setTimeout(() => {
      this.getMenuItems();
    }, 500);
    this.cols = [
      { field: 'id', header: 'ID' },
      { field: 'name', header: 'Nombre' },
      { field: 'colorCode', header: 'Color' },
    ];
  }

  getMenuItems() {
    if (this.isAllow(PermissionEnum.UPDATE)) {
      this.items.push({
        label: 'Editar',
        icon: 'pi pi-pencil',
        command: (e) => this.editColor(this.colorSelect)
      });
    }
    if (this.isAllow(PermissionEnum.DELETE)) {
      this.items.push({
        label: 'Eliminar',
        icon: 'pi pi-trash',
        command: (e) => this.deleteColor(this.colorSelect)
      });
    }
  }

  openNew() {
    this.color = {} as ColorCatalog;
    this.submitted = false;
    this.colorDialog = true;
  }

  editColor(color: ColorCatalog) {
    this.color = { ...color };
    this.colorDialog = true;
    this.isEdit = true;
  }

  deleteColor(color: ColorCatalog) {
    this.confirmationService.confirm({
      message:
        'Estas seguro de eliminar el color ' + color.name + '?',
      header: 'Confirmación',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.colorCService.delete(color.id).subscribe(
          (res) => {
            this.messageService.add({
              severity: 'success',
              summary: 'Éxito',
              detail: 'Color A Eliminado',
              life: 3000,
            });
            this.colors = [];
            this.getAll();
          },
          (err) => {
            this.messageService.add({
              severity: 'error',
              summary: 'Error',
              detail: err.error,
              life: 3000,
            });
          }
        );

      },
    });
  }

  deleteSelectedColors() {
    this.confirmationService.confirm({
      message: 'Estás seguro de eliminar los colores seleccionados?',
      header: 'Confirmación',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.selectedColor.forEach(color => {
          this.colorCService.delete(color.id).subscribe(
            (res) => {
              this.messageService.add({
                severity: 'success',
                summary: 'Éxito',
                detail: 'Color Eliminado',
                life: 3000,
              });
              this.colors = [];
              this.getAll();
            },
            (err) => {
              this.messageService.add({
                severity: 'error',
                summary: 'Error',
                detail: err.error,
                life: 3000,
              });
            }
          );
        });
      },
    });
  }

  saveColor() {
    this.submitted = true;
    if (this.isEdit) {
      this.colorCService.update(this.color.id, this.color).subscribe(
        (res) => {
          this.messageService.add({
            severity: 'success',
            summary: 'Éxito',
            detail: 'Color C Actualizado',
            life: 3000,
          });
          this.colors = [];
          this.getAll();
          this.isEdit = false;
        },
        (err) => {
          this.messageService.add({
            severity: 'error',
            summary: 'Error',
            detail: err.error,
            life: 3000,
          });
        }
      );
    } else if (this.isValidToSave()) {
      this.colorCService.create(this.color).subscribe(
        (res) => {
          this.messageService.add({
            severity: 'success',
            summary: 'Éxito',
            detail: 'Color C Creado',
            life: 3000,
          });
          this.colors = [];
          this.getAll();
        },
        (err) => {
          this.messageService.add({
            severity: 'error',
            summary: 'Error',
            detail: err.error,
            life: 3000,
          });
        }
      );
    } else {
      this.messageService.add({
        severity: 'warn',
        summary: 'Atención',
        detail: 'Llene todos los campos',
        life: 3000,
      });
      this.colorDialog = true;
      return;
    }

    this.colors = [...this.colors];
    this.colorDialog = false;
    this.color = {} as ColorCatalog;
  }

  hideDialog() {
    this.colorDialog = false;
    this.submitted = false;
  }

  getAll() {
    this.colorCService.getAll().subscribe((colors) => {
      this.colors = colors;
      this.loading = false;
    });
  }

  isValidToSave(): boolean {
    return !!(this.color.name && this.color.colorCode);
  }

  getPermissionsPage() {
    this.permissionService.findPermissionPage().subscribe(
      (data) => {
        this.permissionsPage = data;
      }
    );
  }

  isAllow(id: number): boolean {
    if (this.permissionsPage) {
      return this.permissionsPage.find(permission => permission.id === id).flag;
    }
    return false;
  }

}
