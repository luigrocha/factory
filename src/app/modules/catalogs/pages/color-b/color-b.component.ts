import { Component, OnInit } from '@angular/core';
import { ConfirmationService, MessageService } from 'primeng/api';
import { ColorAService } from 'src/app/core/http/catalogs/color-a/color-a.service';
import { ColorBService } from 'src/app/core/http/catalogs/color-b/color-b.service';
import { PermissionService } from 'src/app/core/http/permissions/permission.service';
import { BreadcrumbService } from 'src/app/core/services/breadcrumb.service';
import { ColorA } from 'src/app/types/colorA.types';
import { ColorB } from 'src/app/types/colorB.types';
import { TypePermission } from 'src/app/types/permission';

@Component({
  selector: 'app-color-b',
  templateUrl: './color-b.component.html',
  styleUrls: ['./color-b.component.scss'],
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
export class ColorBComponent implements OnInit {

  colorDialog: boolean;

  selectedColor: ColorB[];

  submitted: boolean;

  cols: any[];

  colors: ColorB[];

  color: ColorB;

  loading = true;

  colorsA: ColorA[];

  colorASelected: any;

  isEditing: boolean;

  permissionsPage: TypePermission[];

  constructor(
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private breadcrumbService: BreadcrumbService,
    private colorBService: ColorBService,
    private colorAService: ColorAService,
    private permissionService: PermissionService,
  ) {
    this.breadcrumbService.setItems([
      { label: 'Diseño' },
      { label: 'Catálogos' },
      { label: 'Colores B', routerLink: ['home/catalogs/colores-b'] },
    ]);
  }

  ngOnInit() {
    this.getPermissionsPage();
    this.getAll();
    this.getColorsA();
    this.cols = [
      { field: 'color', header: 'Color' },
      { field: 'colorA', header: 'Color Primario' },
      { field: 'index', header: 'Indice' },
      { field: 'dosage', header: 'Dosis' },
      { field: 'description', header: 'Descripción' },
    ];
  }

  openNew() {
    this.color = {};
    this.submitted = false;
    this.colorDialog = true;
  }

  editColor(color: ColorB) {
    this.color = { ...color };
    this.colorDialog = true;
    this.isEditing = true;
  }

  deleteColor(color: ColorB) {
    this.confirmationService.confirm({
      message:
        'Estas seguro de eliminar el color ' + color.description + '?',
      header: 'Confirmación',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.colorBService.delete(color.id).subscribe(
          (res) => {
            this.messageService.add({
              severity: 'success',
              summary: 'Éxito',
              detail: 'Color B Eliminado',
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
          this.colorBService.delete(color.id).subscribe(
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
        })

        this.selectedColor = null;
        this.messageService.add({
          severity: 'success',
          summary: 'Correcto',
          detail: 'Colores Elimnados',
          life: 3000,
        });
      },
    });
  }

  saveColor() {
    this.submitted = true;

    if (this.isEditing) {
      this.colorBService.update(this.color.id, this.color).subscribe(
        (res) => {
          this.messageService.add({
            severity: 'success',
            summary: 'Éxito',
            detail: 'Color B Actualizado',
            life: 3000,
          });
          this.colors = [];
          this.getAll();
          this.isEditing = false;
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
      this.colorBService.create(this.color).subscribe(
        (res) => {
          this.messageService.add({
            severity: 'success',
            summary: 'Éxito',
            detail: 'Color B Creado',
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
    this.color = {};
  }

  hideDialog() {
    this.colorDialog = false;
    this.submitted = false;
  }

  getAll() {
    this.colorBService.getAll().subscribe((colors) => {
      this.colors = colors;
      this.loading = false;
    });
  }

  getColorsA() {
    this.colorAService.getAll().subscribe((colorsA) => {
      this.colorsA = colorsA;
    });
  }

  onColorASelected(e: any) {
    const value = e.value;
    const filter = this.colors.filter(color => color.colorA.id === value.id);
    const numIndex = filter.length + 1;
    this.color.id = value.id + numIndex;
    this.color.index = numIndex;
  }

  isValidToSave(): boolean {
    return this.color.id && this.color.colorA && this.color.index && this.color.dosage && this.color.description ? true : false;
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
