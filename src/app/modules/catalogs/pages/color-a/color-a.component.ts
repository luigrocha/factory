import { Component, OnInit } from '@angular/core';
import { ConfirmationService, MenuItem } from 'primeng/api';
import { PermissionEnum } from 'src/app/core/constants/permisions';
import { ColorAService } from 'src/app/core/http/catalogs/color-a/color-a.service';
import { PermissionService } from 'src/app/core/http/permissions/permission.service';
import { BreadcrumbService } from 'src/app/core/services/breadcrumb.service';
import { ColorA } from 'src/app/types/colorA.types';
import { TypePermission } from 'src/app/types/permission';
import { TableColumn } from 'src/app/types/table.types';
import { TABLE_REPORT_TEMPLATE } from 'src/app/core/constants/table';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { ToastService } from 'src/app/core/services/toast.service';
import { checkIfOptionIsAllowed } from 'src/app/core/utils/permission';
import { ColorAModalComponent } from 'src/app/modules/catalogs/components/color-a-modal/color-a-modal.component';

@Component({
  selector: 'app-color-a',
  templateUrl: './color-a.component.html',
  styleUrls: ['./color-a.component.scss'],
  providers: [ConfirmationService],
})
export class ColorAComponent implements OnInit {

  columns: TableColumn<ColorA>[];
  pageSize: number = 10;
  colors: ColorA[] = [];
  tableReportTemplate = TABLE_REPORT_TEMPLATE;
  rowsPerPageOptions: number[] = [5, 10, 20, 50, 100];
  addDialogRef: DynamicDialogRef;
  selectedColor: ColorA;
  menuItems: MenuItem[] = [];
  permissionsPage: TypePermission[];
  selectedColors: ColorA[];

  constructor(
    private toastService: ToastService,
    private confirmationService: ConfirmationService,
    private breadcrumbService: BreadcrumbService,
    private colorAService: ColorAService,
    private permissionService: PermissionService,
    private dialogService: DialogService,
  ) {
    this.breadcrumbService.setItems([
      {label: 'Diseño'},
      {label: 'Catálogos'},
      {label: 'Colores A', routerLink: ['home/catalogs/colores-a']},
    ]);
  }

  ngOnInit() {
    this.getPermissionsPage();
    this.getAllColors();
    this.columns = [
      {field: 'color', header: 'ID'},
      {field: 'name', header: 'Nombre'},
      {field: 'colorCode', header: 'Color'},
    ];
  }

  getMenuItems() {
    if (this.isAllow(PermissionEnum.UPDATE)) {
      this.menuItems.push({
        label: 'Editar',
        icon: 'pi pi-pencil',
        command: () => this.editColor()
      });
    }
    if (this.isAllow(PermissionEnum.DELETE)) {
      this.menuItems.push({
        label: 'Eliminar',
        icon: 'pi pi-trash',
        command: () => this.deleteColor()
      });
    }
  }

  editColor(): void {
    this.addDialogRef = this.dialogService.open(ColorAModalComponent, {
      data: this.selectedColor,
      header: `Actualizar color A ${this.selectedColor.name}`,
      width: '450px',
      contentStyle: {'max-width': '100%', 'overflow': 'auto'},
    });

    this.addDialogRef.onClose
      .subscribe((color: ColorA) => {
        if (color) {
          this.colorAService.update(this.selectedColor.id, color)
            .subscribe(() => {
              this.toastService.success('Color A actualizado correctamente');
              this.getAllColors();
            });
        }
      });
  }

  deleteColor() {
    this.confirmationService.confirm({
      message:
        '¿Estas seguro de eliminar el color ' + this.selectedColor.name + '?',
      header: 'Confirmación',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.colorAService.delete(this.selectedColor.id)
          .subscribe(deleted => {
            if (deleted) {
              this.toastService.success('Color A eliminado correctamente');
              this.getAllColors();
            } else {
              this.toastService.error('Error al eliminar el color A');
            }
          });
      },
    });
  }

  deleteSelectedColors() {
    this.confirmationService.confirm({
      message: '¿Estás seguro de eliminar los colores seleccionados?',
      header: 'Confirmación',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.selectedColors.forEach(color => {
          this.colorAService.delete(color.id)
            .subscribe(deleted => {
              if (deleted) {
                this.toastService.success('Color A eliminado correctamente');
                this.getAllColors();
              } else {
                this.toastService.error('Error al eliminar el color A');
              }
            });
        });
        this.selectedColors = [];
      },
    });
  }

  getAllColors() {
    this.colorAService.getAll()
      .subscribe(colors => {
        this.colors = colors;
      });
  }

  getPermissionsPage() {
    this.permissionService.findPermissionPage()
      .subscribe(permissions => {
        this.permissionsPage = permissions;
        this.getMenuItems();
      });
  }

  isAllow(id: number): boolean {
    return checkIfOptionIsAllowed(this.permissionsPage, id);
  }

  createColor(): void {
    this.addDialogRef = this.dialogService.open(ColorAModalComponent, {
      header: 'Crear nuevo color A',
      width: '450px',
      contentStyle: {'max-width': '100%', 'overflow': 'auto'},
    });

    this.addDialogRef.onClose
      .subscribe((color: ColorA) => {
        if (color) {
          this.colorAService.create(color)
            .subscribe(() => {
              this.toastService.success('Color A creado correctamente');
              this.getAllColors();
            });
        }
      });
  }
}
