import { Component, OnInit } from '@angular/core';
import { ConfirmationService, MenuItem } from 'primeng/api';
import { PermissionEnum } from 'src/app/core/constants/permisions';
import { ColorCService } from 'src/app/core/http/catalogs/color-c/color-c.service';
import { PermissionService } from 'src/app/core/http/permissions/permission.service';
import { BreadcrumbService } from 'src/app/core/services/breadcrumb.service';
import { TypePermission } from 'src/app/types/permission';
import { ColorCatalog, CreateColorCatalog, UpdateColorCatalog } from 'src/app/types/color-catalog.types';
import { TableColumn } from 'src/app/types/table.types';
import { TABLE_REPORT_TEMPLATE } from 'src/app/core/constants/table';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { ToastService } from 'src/app/core/services/toast.service';
import { checkIfOptionIsAllowed } from 'src/app/core/utils/permission';
import { ColorCModalComponent } from '../../components/color-c-modal/color-c-modal.component';

@Component({
  selector: 'app-color-c',
  templateUrl: './color-c.component.html',
  styleUrls: ['./color-c.component.scss'],
  providers: [ConfirmationService],
})
export class ColorCComponent implements OnInit {

  columns: TableColumn<ColorCatalog>[];
  pageSize: number = 10;
  colors: ColorCatalog[] = [];
  tableReportTemplate = TABLE_REPORT_TEMPLATE;
  rowsPerPageOptions: number[] = [5, 10, 20, 50, 100];
  addDialogRef: DynamicDialogRef;
  selectedColor: ColorCatalog;
  menuItems: MenuItem[] = [];
  permissionsPage: TypePermission[];
  selectedColors: ColorCatalog[];

  constructor(
    private toastService: ToastService,
    private confirmationService: ConfirmationService,
    private breadcrumbService: BreadcrumbService,
    private colorCService: ColorCService,
    private permissionService: PermissionService,
    private dialogService: DialogService,
  ) {
    this.breadcrumbService.setItems([
      {label: 'Diseño'},
      {label: 'Catálogos'},
      {label: 'Colores C', routerLink: ['home/catalogs/colores-c']},
    ]);
  }

  ngOnInit() {
    this.getPermissionsPage();
    this.getAllColors();
    this.columns = [
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

  createColor(): void {
    this.addDialogRef = this.dialogService.open(ColorCModalComponent, {
      header: 'Crear nuevo color C',
      width: '450px',
      contentStyle: {'max-width': '100%', 'overflow': 'auto'},
    });

    this.addDialogRef.onClose
      .subscribe((color: CreateColorCatalog) => {
        if (color) {
          this.colorCService.create(color)
            .subscribe(() => {
              this.toastService.success('Color C creado correctamente');
              this.getAllColors();
            });
        }
      });
  }

  editColor() {
    this.addDialogRef = this.dialogService.open(ColorCModalComponent, {
      data: this.selectedColor,
      header: `Actualizar color C ${this.selectedColor.name}`,
      width: '450px',
      contentStyle: {'max-width': '100%', 'overflow': 'auto'},
    });

    this.addDialogRef.onClose
      .subscribe((color: UpdateColorCatalog) => {
        if (color) {
          this.colorCService.update(this.selectedColor.id, color)
            .subscribe(() => {
              this.toastService.success('Color C actualizado correctamente');
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
        this.colorCService.delete(this.selectedColor.id)
          .subscribe(deleted => {
            if (deleted) {
              this.toastService.success('Color eliminado correctamente');
              this.getAllColors();
            } else {
              this.toastService.error('Error al eliminar el color');
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
          this.colorCService.delete(color.id)
            .subscribe(deleted => {
              if (deleted) {
                this.toastService.success('Color eliminado correctamente');
                this.getAllColors();
              } else {
                this.toastService.error('Error al eliminar el color');
              }
            });
        });
        this.selectedColors = [];
      },
    });
  }

  getAllColors() {
    this.colorCService.getAll()
      .subscribe(colors => {
        this.colors = colors;
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
