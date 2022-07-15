import { Component, OnInit } from '@angular/core';
import { ConfirmationService, MenuItem } from 'primeng/api';
import { PermissionEnum } from 'src/app/core/constants/permisions';
import { LocationService } from 'src/app/core/http/catalogs/location/location.service';
import { PermissionService } from 'src/app/core/http/permissions/permission.service';
import { BreadcrumbService } from 'src/app/core/services/breadcrumb.service';
import { CreateLocation, Location } from 'src/app/types/celler.types';
import { TypePermission } from 'src/app/types/permission';
import { TableColumn } from 'src/app/types/table.types';
import { TABLE_REPORT_TEMPLATE } from 'src/app/core/constants/table';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { checkIfOptionIsAllowed } from 'src/app/core/utils/permission';
import { ToastService } from 'src/app/core/services/toast.service';
import { LocationModalComponent } from 'src/app/modules/catalogs/components/location-modal/location-modal.component';

@Component({
  selector: 'app-location',
  templateUrl: './location.component.html',
  styleUrls: ['./location.component.scss'],
  providers: [ConfirmationService],
})
export class LocationComponent implements OnInit {

  columns: TableColumn<Location>[];
  pageSize: number = 10;
  locations: Location[] = [];
  tableReportTemplate = TABLE_REPORT_TEMPLATE;
  rowsPerPageOptions: number[] = [5, 10, 20, 50, 100];
  addDialogRef: DynamicDialogRef;
  selectedLocation: Location;
  menuItems: MenuItem[] = [];
  permissionsPage: TypePermission[];
  selectedLocations: Location[];

  constructor(
    private toastService: ToastService,
    private confirmationService: ConfirmationService,
    private breadcrumbService: BreadcrumbService,
    private locationService: LocationService,
    private permissionService: PermissionService,
    private dialogService: DialogService
  ) {
    this.breadcrumbService.setItems([
      {label: 'Diseño'},
      {label: 'Catálogos'},
      {label: 'Ubicaciones', routerLink: ['home/catalogs/ubicacion']},
    ]);
  }

  ngOnInit() {
    this.getPermissionsPage();
    this.getAllLocations();
    this.columns = [
      {field: 'location', header: 'Ubicación'},
      {field: 'description', header: 'Descripción'},
    ];
  }

  getMenuItems() {
    if (this.isAllow(PermissionEnum.UPDATE)) {
      this.menuItems.push({
        label: 'Editar',
        icon: 'pi pi-pencil',
        command: () => this.editLocation()
      });
    }
    if (this.isAllow(PermissionEnum.DELETE)) {
      this.menuItems.push({
        label: 'Eliminar',
        icon: 'pi pi-trash',
        command: () => this.deleteLocation()
      });
    }
  }

  editLocation() {
    this.addDialogRef = this.dialogService.open(LocationModalComponent, {
      data: this.selectedLocation,
      header: `Actualizar ubicación ${this.selectedLocation.location}`,
      width: '450px',
      contentStyle: {'max-width': '100%', 'overflow': 'auto'},
    });

    this.addDialogRef.onClose
      .subscribe((location: Location) => {
        if (location) {
          this.locationService.update(this.selectedLocation.id, location)
            .subscribe(() => {
              this.toastService.success('Ubicación actualizada correctamente');
              this.getAllLocations();
            });
        }
      });
  }

  deleteLocation() {
    this.confirmationService.confirm({
      message:
        '¿Estas seguro de eliminar la ubicación ' + this.selectedLocation.location + '?',
      header: 'Confirmación',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.locationService.delete(this.selectedLocation.id)
          .subscribe(deleted => {
            if (deleted) {
              this.toastService.success('Ubicación eliminada correctamente');
              this.getAllLocations();
            } else {
              this.toastService.error('Error al eliminar la ubicación');
            }
          });
      },
    });
  }

  deleteSelectedLocations() {
    this.confirmationService.confirm({
      message: '¿Estás seguro de eliminar las ubicaciones seleccionadas?',
      header: 'Confirmación',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.selectedLocations.forEach(location => {
          this.locationService.delete(location.id)
            .subscribe(deleted => {
              if (deleted) {
                this.toastService.success('Ubicación eliminada correctamente');
                this.getAllLocations();
              } else {
                this.toastService.error('Error al eliminar la ubicación');
              }
            });
        });
        this.selectedLocations = [];
      },
    });
  }

  getAllLocations() {
    this.locationService.getAll()
      .subscribe((locations) => {
        this.locations = locations;
      });
  }

  getPermissionsPage() {
    this.permissionService.findPermissionPage()
      .subscribe(permissions => {
          this.permissionsPage = permissions;
          this.getMenuItems();
        }
      );
  }

  isAllow(id: number): boolean {
    return checkIfOptionIsAllowed(this.permissionsPage, id);
  }

  createLocation() {
    this.addDialogRef = this.dialogService.open(LocationModalComponent, {
      header: 'Crear nueva ubicación',
      width: '450px',
      contentStyle: {'max-width': '100%', 'overflow': 'auto'},
    });

    this.addDialogRef.onClose
      .subscribe((location: CreateLocation) => {
        if (location) {
          this.locationService.create(location)
            .subscribe(() => {
              this.toastService.success('Ubicación creada correctamente');
              this.getAllLocations();
            });
        }
      });
  }
}
