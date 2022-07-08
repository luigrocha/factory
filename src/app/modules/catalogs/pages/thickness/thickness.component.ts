import { Component, OnInit } from '@angular/core';
import { ConfirmationService, MenuItem } from 'primeng/api';
import { PermissionEnum } from 'src/app/core/constants/permisions';
import { ThicknessService } from 'src/app/core/http/catalogs/thickness/thickness.service';
import { PermissionService } from 'src/app/core/http/permissions/permission.service';
import { BreadcrumbService } from 'src/app/core/services/breadcrumb.service';
import { TypePermission } from 'src/app/types/permission';
import { CreateThickness, Thickness, UpdateThickness } from 'src/app/types/thickness.types';
import { TableColumn } from 'src/app/types/table.types';
import { TABLE_REPORT_TEMPLATE } from 'src/app/core/constants/table';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { ThicknessModalComponent } from 'src/app/modules/catalogs/components/thickness-modal/thickness-modal.component';
import { ToastService } from 'src/app/core/services/toast.service';

@Component({
  selector: 'app-thickness',
  templateUrl: './thickness.component.html',
  styleUrls: ['./thickness.component.scss'],
  providers: [ConfirmationService],
})
export class ThicknessComponent implements OnInit {

  columns: TableColumn<Thickness>[];
  pageSize: number = 10;
  thickness: Thickness[] = [];
  tableReportTemplate = TABLE_REPORT_TEMPLATE;
  rowsPerPageOptions: number[] = [5, 10, 20, 50, 100];
  addDialogRef: DynamicDialogRef;
  selectedThickness: Thickness;
  menuItems: MenuItem[] = [];
  permissionsPage: TypePermission[];
  selectedThicknesses: Thickness[];

  constructor(
    private toastService: ToastService,
    private confirmationService: ConfirmationService,
    private breadcrumbService: BreadcrumbService,
    private thicknessService: ThicknessService,
    private permissionService: PermissionService,
    private dialogService: DialogService,
  ) {
    this.breadcrumbService.setItems([
      {label: 'Diseño'},
      {label: 'Catálogos'},
      {label: 'Grosor', routerLink: ['home/catalogs/grosor']},
    ]);
  }

  ngOnInit() {
    this.getPermissionsPage();
    this.getAllThickness();
    setTimeout(() => {
      this.getMenuItems();
    }, 1000);
    this.columns = [
      {field: 'weight', header: 'Peso'},
      {field: 'thick', header: 'Grosor'},
    ];
  }

  getMenuItems() {
    if (this.isAllow(PermissionEnum.UPDATE)) {
      this.menuItems.push({
        label: 'Editar',
        icon: 'pi pi-pencil',
        command: (e) => this.editThickness()
      });
    }
    if (this.isAllow(PermissionEnum.DELETE)) {
      this.menuItems.push({
        label: 'Eliminar',
        icon: 'pi pi-trash',
        command: (e) => this.deleteThickness()
      });
    }
  }

  editThickness() {
    this.addDialogRef = this.dialogService.open(ThicknessModalComponent, {
      data: this.selectedThickness,
      header: `Actualizar grosor ${this.selectedThickness.weight}`,
      width: '450px',
      contentStyle: {'max-width': '100%', 'overflow': 'auto'},
    });

    this.addDialogRef.onClose
      .subscribe((tick: UpdateThickness) => {
        if (tick) {
          this.thicknessService.update(this.selectedThickness.id, tick)
            .subscribe(() => {
              this.toastService.success('Grosor actualizado correctamente');
              this.getAllThickness();
            });
        }
      });
  }

  deleteThickness() {
    this.confirmationService.confirm({
      message:
        '¿Estas seguro de eliminar el grosor ' + this.selectedThickness.weight + '?',
      header: 'Confirmación',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.thicknessService.delete(this.selectedThickness.id)
          .subscribe(deleted => {
              if (deleted) {
                this.toastService.success('Grosor eliminado correctamente');
                this.getAllThickness();
              } else {
                this.toastService.error('Error al eliminar grosor');
              }
            }
          );
      },
    });
  }

  deleteSelectedThicknesss() {
    this.confirmationService.confirm({
      message: '¿Estás seguro de eliminar los registros seleccionados?',
      header: 'Confirmación',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.selectedThicknesses.forEach(thickness => {
          this.thicknessService.delete(thickness.id)
            .subscribe(deleted => {
              if (deleted) {
                this.toastService.success('Grosor eliminado correctamente');
                this.getAllThickness();
              } else {
                this.toastService.error('Error al eliminar grosor');
              }
            }
          );
        });
      },
    });
  }

  getAllThickness() {
    this.thicknessService.getAll()
      .subscribe((thickness) => {
        this.thickness = thickness;
      });
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

  createThickness() {
    this.addDialogRef = this.dialogService.open(ThicknessModalComponent, {
      header: 'Crear nuevo grosor',
      width: '450px',
      contentStyle: {'max-width': '100%', 'overflow': 'auto'},
    });

    this.addDialogRef.onClose
      .subscribe((thick: CreateThickness) => {
        if (thick) {
          this.thicknessService.create(thick)
            .subscribe(() => {
              this.toastService.success('Grosor creado correctamente');
              this.getAllThickness();
            });
        }
      });
  }
}
