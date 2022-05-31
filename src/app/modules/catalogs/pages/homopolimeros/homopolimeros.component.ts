import { Component, OnInit } from '@angular/core';
import { ConfirmationService, MessageService } from 'primeng/api';
import { HomopolimerosService } from 'src/app/core/http/catalogs/homopolimeros/homopolimeros.service';
import { PermissionService } from 'src/app/core/http/permissions/permission.service';
import { BreadcrumbService } from 'src/app/core/services/breadcrumb.service';
import { Homopolimero } from 'src/app/types/homopolimero.types';
import { TypePermission } from 'src/app/types/permission';

@Component({
  selector: 'app-homopolimeros',
  templateUrl: './homopolimeros.component.html',
  styleUrls: ['./homopolimeros.component.scss'],
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
export class HomopolimerosComponent implements OnInit {

  homoDialog: boolean;

  selectedHomo: Homopolimero[];

  submitted: boolean;

  cols: any[];

  homos: Homopolimero[];

  homo: Homopolimero;

  loading = true;

  permissionsPage: TypePermission[];

  constructor(
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private breadcrumbService: BreadcrumbService,
    private homoService: HomopolimerosService,
    private permissionService: PermissionService,
  ) {
    this.breadcrumbService.setItems([
      { label: 'Diseño' },
      { label: 'Catálogos' },
      { label: 'Homopolímeros', routerLink: ['home/catalogs/homopolimeros'] },
    ]);
  }

  ngOnInit() {
    this.getPermissionsPage();
    this.getAll();
    this.cols = [
      { field: 'percent', header: 'Porcentaje' },
      { field: 'hp', header: 'HP' },
    ];
  }

  openNew() {
    this.homo = {};
    this.submitted = false;
    this.homoDialog = true;
  }

  editHomo(homo: Homopolimero) {
    this.homo = { ...homo };
    this.homoDialog = true;
  }

  deleteHomo(homo: Homopolimero) {
    this.confirmationService.confirm({
      message:
        'Estas seguro de eliminar el homopolímero ' + homo.hpCode + '?',
      header: 'Confirmación',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.homoService.delete(homo.id).subscribe(
          (res) => {
            this.messageService.add({
              severity: 'success',
              summary: 'Éxito',
              detail: 'Homopolímero Eliminado',
              life: 3000,
            });
            this.homos = [];
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

  deleteSelectedHomo() {
    this.confirmationService.confirm({
      message: 'Estás seguro de eliminar los homopolímeros seleccionados?',
      header: 'Confirmación',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.selectedHomo.forEach(homo => {
          this.homoService.delete(homo.id).subscribe(
            (res) => {
              this.messageService.add({
                severity: 'success',
                summary: 'Éxito',
                detail: 'Homopolímero Eliminado',
                life: 3000,
              });
              this.homos = [];
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
        this.selectedHomo = null;
        this.messageService.add({
          severity: 'success',
          summary: 'Correcto',
          detail: 'Homopolímeros Elimnados',
          life: 3000,
        });
      },
    });
  }

  saveHomo() {
    this.submitted = true;
    if (this.homo.id) {
      this.homoService.update(this.homo.id, this.homo).subscribe(
        (res) => {
          this.messageService.add({
            severity: 'success',
            summary: 'Éxito',
            detail: 'Homopolímero Actualizado',
            life: 3000,
          });
          this.homos = [];
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
    } else if (this.isValidToSave()) {
      this.homoService.create(this.homo).subscribe(
        (res) => {
          this.messageService.add({
            severity: 'success',
            summary: 'Éxito',
            detail: 'Homopolímero Creado',
            life: 3000,
          });
          this.homos = [];
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
      this.homoDialog = true;
      return;
    }

    this.homos = [...this.homos];
    this.homoDialog = false;
    this.homo = {};
  }

  hideDialog() {
    this.homoDialog = false;
    this.submitted = false;
  }

  getAll() {
    this.homoService.getAll().subscribe((homos) => {
      this.homos = homos;
      this.loading = false;
    });
  }

  isValidToSave(): boolean {
    return this.homo.percentage && this.homo.hpCode ? true : false;
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
