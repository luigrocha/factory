import { Component, OnInit } from '@angular/core';
import { ConfirmationService, MenuItem } from 'primeng/api';
import { PermissionEnum } from 'src/app/core/constants/permisions';
import { PrinterService } from 'src/app/core/http/catalogs/printers/printer.service';
import { PermissionService } from 'src/app/core/http/permissions/permission.service';
import { BreadcrumbService } from 'src/app/core/services/breadcrumb.service';
import { TypePermission } from 'src/app/types/permission';
import { CreatePrinter, Printer, UpdatePrinter } from 'src/app/types/printer.types';
import { TableColumn } from 'src/app/types/table.types';
import { TABLE_REPORT_TEMPLATE } from 'src/app/core/constants/table';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { ToastService } from 'src/app/core/services/toast.service';
import { checkIfOptionIsAllowed } from 'src/app/core/utils/permission';
import { ThicknessModalComponent } from 'src/app/modules/catalogs/components/thickness-modal/thickness-modal.component';
import { CreateThickness, UpdateThickness } from 'src/app/types/thickness.types';
import { PrinterModalComponent } from 'src/app/modules/catalogs/components/printer-modal/printer-modal.component';

@Component({
  selector: 'app-printers',
  templateUrl: './printers.component.html',
  styleUrls: ['./printers.component.scss'],
  providers: [ConfirmationService],
})
export class PrintersComponent implements OnInit {

  columns: TableColumn<Printer>[];
  pageSize: number = 10;
  printers: Printer[] = [];
  tableReportTemplate = TABLE_REPORT_TEMPLATE;
  rowsPerPageOptions: number[] = [5, 10, 20, 50, 100];
  addDialogRef: DynamicDialogRef;
  selectedPrinter: Printer;
  menuItems: MenuItem[] = [];
  permissionsPage: TypePermission[];
  selectedPrinters: Printer[];

  constructor(
    private toastService: ToastService,
    private confirmationService: ConfirmationService,
    private breadcrumbService: BreadcrumbService,
    private printerService: PrinterService,
    private permissionService: PermissionService,
    private dialogService: DialogService
  ) {
    this.breadcrumbService.setItems([
      {label: 'Diseño'},
      {label: 'Catálogos'},
      {label: 'Impresoras', routerLink: ['home/catalogs/impresoras']},
    ]);
  }

  ngOnInit() {
    this.getPermissionsPage();
    this.getAllPrinters();
    this.columns = [
      {field: 'name', header: 'Nombre'},
      {field: 'numColors', header: 'N° Colores'},
      {field: 'description', header: 'Descripción'},
    ];
  }

  getMenuItems() {
    if (this.isAllow(PermissionEnum.UPDATE)) {
      this.menuItems.push({
        label: 'Editar',
        icon: 'pi pi-pencil',
        command: () => this.editPrinter()
      });
    }
    if (this.isAllow(PermissionEnum.DELETE)) {
      this.menuItems.push({
        label: 'Eliminar',
        icon: 'pi pi-trash',
        command: () => this.deletePrinter()
      });
    }
  }

  editPrinter() {
    this.addDialogRef = this.dialogService.open(PrinterModalComponent, {
      data: this.selectedPrinter,
      header: `Actualizar impresora ${this.selectedPrinter.name}`,
      width: '450px',
      contentStyle: {'max-width': '100%', 'overflow': 'auto'},
    });

    this.addDialogRef.onClose
      .subscribe((printer: UpdatePrinter) => {
        if (printer) {
          this.printerService.update(this.selectedPrinter.id, printer)
            .subscribe(() => {
              this.toastService.success('Impresora actualizada correctamente');
              this.getAllPrinters();
            });
        }
      });
  }

  deletePrinter() {
    this.confirmationService.confirm({
      message:
        '¿Estas seguro de eliminar la impresora ' + this.selectedPrinter.name + '?',
      header: 'Confirmación',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.printerService.delete(this.selectedPrinter.id)
          .subscribe(deleted => {
            if (deleted) {
              this.toastService.success('Impresora eliminada correctamente');
              this.getAllPrinters();
            } else {
              this.toastService.error('Error al eliminar la impresora');
            }
          });
      },
    });
  }

  deleteSelectedPrinters() {
    this.confirmationService.confirm({
      message: '¿Estás seguro de eliminar las impresoras seleccionados?',
      header: 'Confirmación',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.selectedPrinters.forEach(printer => {
          this.printerService.delete(printer.id)
            .subscribe(deleted => {
              if (deleted) {
                this.toastService.success('Impresora eliminada correctamente');
                this.getAllPrinters();
              } else {
                this.toastService.error('Error al eliminar la impresora');
              }
            });
          this.selectedPrinters = [];
        });
      }
    });
  }

  getAllPrinters() {
    this.printerService.getAll()
      .subscribe(printers => {
        this.printers = printers;
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

  createPrinter() {
    this.addDialogRef = this.dialogService.open(PrinterModalComponent, {
      header: 'Crear nueva impresora',
      width: '450px',
      contentStyle: {'max-width': '100%', 'overflow': 'auto'},
    });

    this.addDialogRef.onClose
      .subscribe((printer: CreatePrinter) => {
        if (printer) {
          this.printerService.create(printer)
            .subscribe(() => {
              this.toastService.success('Impresora creada correctamente');
              this.getAllPrinters();
            });
        }
      });
  }
}
