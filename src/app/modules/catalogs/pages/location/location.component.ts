import { Component, OnInit } from '@angular/core';
import { ConfirmationService, MenuItem, MessageService } from 'primeng/api';
import { PermissionEnum } from 'src/app/core/constants/permisions';
import { LocationService } from 'src/app/core/http/catalogs/location/location.service';
import { PermissionService } from 'src/app/core/http/permissions/permission.service';
import { BreadcrumbService } from 'src/app/core/services/breadcrumb.service';
import { Location } from 'src/app/types/celler.types';
import { TypePermission } from 'src/app/types/permission';

@Component({
  selector: 'app-location',
  templateUrl: './location.component.html',
  styleUrls: ['./location.component.scss'],
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
export class LocationComponent implements OnInit {

  locationDialog: boolean;

  selectedlocation: Location[];

  submitted: boolean;

  cols: any[];

  locations: Location[];

  location: Location;

  loading = true;

  permissionsPage: TypePermission[];

  items: MenuItem[] = [];

  locationSelect: Location;

  constructor(
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private breadcrumbService: BreadcrumbService,
    private locationService: LocationService,
    private permissionService: PermissionService,
  ) {
    this.breadcrumbService.setItems([
      { label: 'Diseño' },
      { label: 'Catálogos' },
      { label: 'Ubicaciones', routerLink: ['home/catalogs/ubicacion'] },
    ]);
  }

  ngOnInit() {
    this.getPermissionsPage();
    this.getAll();
    setTimeout(() => {
      this.getMenuItems();
    }, 500);
    this.cols = [
      { field: 'location', header: 'Ubicación' },
      { field: 'description', header: 'Descripción' },
    ];
  }

  getMenuItems() {
    if (this.isAllow(PermissionEnum.UPDATE)) {
      this.items.push({
        label: 'Editar',
        icon: 'pi pi-pencil',
        command: (e) => this.editHomo(this.locationSelect)
      });
    }
    if (this.isAllow(PermissionEnum.DELETE)) {
      this.items.push({
        label: 'Eliminar',
        icon: 'pi pi-trash',
        command: (e) => this.deleteHomo(this.locationSelect)
      });
    }
  }

  openNew() {
    this.location = {};
    this.submitted = false;
    this.locationDialog = true;
  }

  editHomo(location: Location) {
    this.location = { ...location };
    this.locationDialog = true;
  }

  deleteHomo(location: Location) {
    this.confirmationService.confirm({
      message:
        'Estas seguro de eliminar la ubicación ' + location.location + '?',
      header: 'Confirmación',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.locationService.delete(location.id).subscribe(
          (res) => {
            this.messageService.add({
              severity: 'success',
              summary: 'Éxito',
              detail: 'Homopolímero Eliminado',
              life: 3000,
            });
            this.locations = [];
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
      message: 'Estás seguro de eliminar las ubicaciones seleccionados?',
      header: 'Confirmación',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.selectedlocation.forEach(location => {
          this.locationService.delete(location.id).subscribe(
            (res) => {
              this.messageService.add({
                severity: 'success',
                summary: 'Éxito',
                detail: 'Ubicación Eliminada',
                life: 3000,
              });
              this.locations = [];
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
        this.selectedlocation = null;
        this.messageService.add({
          severity: 'success',
          summary: 'Correcto',
          detail: 'Ubicaciones Elimnadas',
          life: 3000,
        });
      },
    });
  }

  saveHomo() {
    this.submitted = true;
    if (this.location.id) {
      this.locationService.update(this.location.id, this.location).subscribe(
        (res) => {
          this.messageService.add({
            severity: 'success',
            summary: 'Éxito',
            detail: 'Ubicación Actualizada',
            life: 3000,
          });
          this.locations = [];
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
      this.locationService.create(this.location).subscribe(
        (res) => {
          this.messageService.add({
            severity: 'success',
            summary: 'Éxito',
            detail: 'Ubicación Creada',
            life: 3000,
          });
          this.locations = [];
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
      this.locationDialog = true;
      return;
    }

    this.locations = [...this.locations];
    this.locationDialog = false;
    this.location = {};
  }

  hideDialog() {
    this.locationDialog = false;
    this.submitted = false;
  }

  getAll() {
    this.locationService.getAll().subscribe((locations) => {
      this.locations = locations;
      this.loading = false;
    });
  }

  isValidToSave(): boolean {
    return this.location.location && this.location.description ? true : false;
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
