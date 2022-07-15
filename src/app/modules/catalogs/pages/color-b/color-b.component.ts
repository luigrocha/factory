import { Component, OnInit } from '@angular/core';
import { ConfirmationService, MenuItem } from 'primeng/api';
import { PermissionEnum } from 'src/app/core/constants/permisions';
import { ColorBService } from 'src/app/core/http/catalogs/color-b/color-b.service';
import { PermissionService } from 'src/app/core/http/permissions/permission.service';
import { BreadcrumbService } from 'src/app/core/services/breadcrumb.service';
import { ColorB, CreateColorB, UpdateColorB } from 'src/app/types/colorB.types';
import { TypePermission } from 'src/app/types/permission';
import { TableColumn } from 'src/app/types/table.types';
import { TABLE_REPORT_TEMPLATE } from 'src/app/core/constants/table';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { ToastService } from 'src/app/core/services/toast.service';
import { checkIfOptionIsAllowed } from 'src/app/core/utils/permission';
import { ColorBModalComponent } from 'src/app/modules/catalogs/components/color-b-modal/color-b-modal.component';

@Component({
  selector: 'app-color-b',
  templateUrl: './color-b.component.html',
  styleUrls: ['./color-b.component.scss'],
  providers: [ConfirmationService],
})
export class ColorBComponent implements OnInit {

  columns: TableColumn<ColorB>[];
  pageSize: number = 10;
  colors: ColorB[] = [];
  tableReportTemplate = TABLE_REPORT_TEMPLATE;
  rowsPerPageOptions: number[] = [5, 10, 20, 50, 100];
  addDialogRef: DynamicDialogRef;
  selectedColor: ColorB;
  menuItems: MenuItem[] = [];
  permissionsPage: TypePermission[];
  selectedColors: ColorB[];

  constructor(
    private toastService: ToastService,
    private confirmationService: ConfirmationService,
    private breadcrumbService: BreadcrumbService,
    private colorBService: ColorBService,
    private permissionService: PermissionService,
    private dialogService: DialogService,
  ) {
    this.breadcrumbService.setItems([
      {label: 'Diseño'},
      {label: 'Catálogos'},
      {label: 'Colores B', routerLink: ['home/catalogs/colores-b']},
    ]);
  }

  ngOnInit() {
    this.getPermissionsPage();
    this.getAllColors();
    this.columns = [
      {field: 'color', header: 'Color'},
      {field: 'colorA.name', header: 'Color Primario'},
      {field: 'index', header: 'Índice'},
      {field: 'dosage', header: 'Dosis'},
      {field: 'description', header: 'Descripción'},
      {field: 'observation', header: 'Observaciones'},
    ];
  }

  getMenuItems() {
    if (this.isAllow(PermissionEnum.UPDATE)) {
      this.menuItems.push({
        label: 'Editar',
        icon: 'pi pi-pencil',
        command: (e) => this.editColor()
      });
    }
    if (this.isAllow(PermissionEnum.DELETE)) {
      this.menuItems.push({
        label: 'Eliminar',
        icon: 'pi pi-trash',
        command: (e) => this.deleteColor()
      });
    }
  }

  editColor(): void {
    this.addDialogRef = this.dialogService.open(ColorBModalComponent, {
      data: this.selectedColor,
      header: `Actualizar color B ${this.selectedColor.description}`,
      width: '450px',
      contentStyle: {'max-width': '100%', 'overflow': 'auto'},
    });

    this.addDialogRef.onClose
      .subscribe((color: UpdateColorB) => {
        if (color) {
          this.colorBService.update(this.selectedColor.id, color)
            .subscribe(() => {
              this.toastService.success('Color B actualizado correctamente');
              this.getAllColors();
            });
        }
      });
  }

  deleteColor(): void {
    this.confirmationService.confirm({
      message:
        '¿Estas seguro de eliminar el color ' + this.selectedColor.description + '?',
      header: 'Confirmación',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.colorBService.delete(this.selectedColor.id)
          .subscribe(deleted => {
              if (deleted) {
                this.toastService.success('Color eliminado correctamente');
                this.getAllColors();
              } else {
                this.toastService.error('Error al eliminar el color');
              }
            }
          );
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
          this.colorBService.delete(color.id)
            .subscribe(deleted => {
                if (deleted) {
                  this.toastService.success('Color eliminado correctamente');
                  this.getAllColors();
                } else {
                  this.toastService.error('Error al eliminar el color');
                }
              }
            );
        });
      },
    });
  }

  getAllColors() {
    this.colorBService.getAll()
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

  createColor(): void {
    this.addDialogRef = this.dialogService.open(ColorBModalComponent, {
      header: 'Crear nuevo color B',
      width: '450px',
      contentStyle: {'max-width': '100%', 'overflow': 'auto'},
    });

    this.addDialogRef.onClose
      .subscribe((color: CreateColorB) => {
        if (color) {
          this.colorBService.create(color)
            .subscribe(() => {
              this.toastService.success('Color B creado correctamente');
              this.getAllColors();
            });
        }
      });
  }
}
