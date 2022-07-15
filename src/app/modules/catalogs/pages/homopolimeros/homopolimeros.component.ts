import { Component, OnInit } from '@angular/core';
import { ConfirmationService, MenuItem, MessageService } from 'primeng/api';
import { PermissionEnum } from 'src/app/core/constants/permisions';
import { HomopolimerosService } from 'src/app/core/http/catalogs/homopolimeros/homopolimeros.service';
import { PermissionService } from 'src/app/core/http/permissions/permission.service';
import { BreadcrumbService } from 'src/app/core/services/breadcrumb.service';
import { CreateHomopolimero, Homopolimero, UpdateHomopolimero } from 'src/app/types/homopolimero.types';
import { TypePermission } from 'src/app/types/permission';
import { TableColumn } from 'src/app/types/table.types';
import { Machine } from 'src/app/types/machine.types';
import { TABLE_REPORT_TEMPLATE } from 'src/app/core/constants/table';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { ToastService } from 'src/app/core/services/toast.service';
import { checkIfOptionIsAllowed } from 'src/app/core/utils/permission';
import { ThicknessModalComponent } from 'src/app/modules/catalogs/components/thickness-modal/thickness-modal.component';
import { CreateThickness } from 'src/app/types/thickness.types';
import {
  HomopolimeroModalComponent
} from 'src/app/modules/catalogs/components/homopolimero-modal/homopolimero-modal.component';

@Component({
  selector: 'app-homopolimeros',
  templateUrl: './homopolimeros.component.html',
  styleUrls: ['./homopolimeros.component.scss'],
  providers: [ConfirmationService],
})
export class HomopolimerosComponent implements OnInit {

  columns: TableColumn<Homopolimero>[];
  pageSize: number = 10;
  homos: Homopolimero[];
  tableReportTemplate = TABLE_REPORT_TEMPLATE;
  rowsPerPageOptions: number[] = [5, 10, 20, 50, 100];
  addDialogRef: DynamicDialogRef;
  selectedHomo: Homopolimero;
  menuItems: MenuItem[] = [];
  selectedHomos: Homopolimero[] = [];
  permissionsPage: TypePermission[];

  constructor(
    private toastService: ToastService,
    private confirmationService: ConfirmationService,
    private breadcrumbService: BreadcrumbService,
    private homoService: HomopolimerosService,
    private permissionService: PermissionService,
    private dialogService: DialogService
  ) {
    this.breadcrumbService.setItems([
      { label: 'Diseño' },
      { label: 'Catálogos' },
      { label: 'Homopolímeros', routerLink: ['home/catalogs/homopolimeros'] },
    ]);
  }

  ngOnInit() {
    this.getPermissionsPage();
    this.getAllHomos();
    this.columns = [
      { field: 'percentage', header: 'Porcentaje' },
      { field: 'hpCode', header: 'HP' },
    ];
  }

  getMenuItems() {
    if (this.isAllow(PermissionEnum.UPDATE)) {
      this.menuItems.push({
        label: 'Editar',
        icon: 'pi pi-pencil',
        command: () => this.editHomo()
      });
    }
    if (this.isAllow(PermissionEnum.DELETE)) {
      this.menuItems.push({
        label: 'Eliminar',
        icon: 'pi pi-trash',
        command: () => this.deleteHomo()
      });
    }
  }

  editHomo(): void {
    this.addDialogRef = this.dialogService.open(HomopolimeroModalComponent, {
      data: this.selectedHomo,
      header: `Actualizar homopolímero ${this.selectedHomo.hpCode}`,
      width: '450px',
      contentStyle: {'max-width': '100%', 'overflow': 'auto'},
    });

    this.addDialogRef.onClose
      .subscribe((homo: UpdateHomopolimero) => {
        if (homo) {
          this.homoService.update(this.selectedHomo.id, homo)
            .subscribe(() => {
              this.toastService.success("Homopolímero actualizado correctamente")
              this.getAllHomos();
            });
        }
      });
  }

  deleteHomo() {
    this.confirmationService.confirm({
      message:
        '¿Estas seguro de eliminar el homopolímero ' + this.selectedHomo.hpCode + '?',
      header: 'Confirmación',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.homoService.delete(this.selectedHomo.id)
          .subscribe(deleted => {
            if (deleted) {
              this.toastService.success('Homopolímero eliminado correctamente');
              this.getAllHomos()
            } else {
              this.toastService.error('Error al eliminar el homopolímero');
            }
          });
      },
    });
  }

  deleteSelectedHomo() {
    this.confirmationService.confirm({
      message: '¿Estás seguro de eliminar los homopolímeros seleccionados?',
      header: 'Confirmación',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.selectedHomos.forEach(homo => {
          this.homoService.delete(homo.id)
            .subscribe(deleted => {
              if (deleted) {
                this.toastService.success('Homopolímero eliminado correctamente');
                this.getAllHomos();
              } else {
                this.toastService.error('Error al eliminar el homopolímero');
              }
            });
        });
        this.selectedHomos = [];
      },
    });
  }

  saveHomo() {
    this.addDialogRef = this.dialogService.open(HomopolimeroModalComponent, {
      header: 'Crear nuevo homopolímero',
      width: '450px',
      contentStyle: {'max-width': '100%', 'overflow': 'auto'},
    });

    this.addDialogRef.onClose
      .subscribe((homo: CreateHomopolimero) => {
        if (homo) {
          this.homoService.create(homo)
            .subscribe(() => {
              this.toastService.success("Homopolímero creado correctamente")
              this.getAllHomos();
            });
        }
      });
  }

  getAllHomos() {
    this.homoService.getAll()
      .subscribe(homos => {
        this.homos = homos;
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
}
