import { Component, OnInit } from '@angular/core';
import { ConfirmationService, MessageService } from 'primeng/api';
import { PrinterService } from 'src/app/core/http/catalogs/printers/printer.service';
import { PermissionService } from 'src/app/core/http/permissions/permission.service';
import { BreadcrumbService } from 'src/app/core/services/breadcrumb.service';
import { TypePermission } from 'src/app/types/permission';
import { Printer } from 'src/app/types/printer.types';

@Component({
  selector: 'app-printers',
  templateUrl: './printers.component.html',
  styleUrls: ['./printers.component.scss'],
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
export class PrintersComponent implements OnInit {

  printerDialog: boolean;

  selectedPrinter: Printer[];

  submitted: boolean;

  cols: any[];

  printers: Printer[];

  printer: Printer;

  loading = true;

  permissionsPage: TypePermission[];

  constructor(
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private breadcrumbService: BreadcrumbService,
    private printerService: PrinterService,
    private permissionService: PermissionService,
  ) {
    this.breadcrumbService.setItems([
      { label: 'Diseño' },
      { label: 'Catálogos' },
      { label: 'Impresoras', routerLink: ['home/catalogs/impresoras'] },
    ]);
  }

  ngOnInit() {
    this.getPermissionsPage();
    this.getAll();
    this.cols = [
      { field: 'name', header: 'Nombre' },
      { field: 'numColors', header: 'N° Colores' },
      { field: 'description', header: 'Descripción' },
    ];
  }

  openNew() {
    this.printer = {};
    this.submitted = false;
    this.printerDialog = true;
  }

  editPrinter(printer: Printer) {
    this.printer = { ...printer };
    this.printerDialog = true;
  }

  deletePrinter(printer: Printer) {
    this.confirmationService.confirm({
      message:
        'Estas seguro de eliminar la impresora ' + printer.name + '?',
      header: 'Confirmación',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.printerService.delete(printer.id).subscribe(
          (res) => {
            this.messageService.add({
              severity: 'success',
              summary: 'Éxito',
              detail: 'Impresora Eliminada',
              life: 3000,
            });
            this.printers = [];
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

  deleteSelectedPrinters() {
    this.confirmationService.confirm({
      message: 'Estás seguro de eliminar las impresoras seleccionados?',
      header: 'Confirmación',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.selectedPrinter.forEach(printer => {
          this.printerService.delete(printer.id).subscribe(
            (res) => {
              this.messageService.add({
                severity: 'success',
                summary: 'Éxito',
                detail: 'Impresora Eliminada',
                life: 3000,
              });
              this.printers = [];
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
        this.selectedPrinter = null;
        this.messageService.add({
          severity: 'success',
          summary: 'Correcto',
          detail: 'Impresoras Elimnadas',
          life: 3000,
        });
      },
    });
  }

  savePrinter() {
    this.submitted = true;

    if (this.printer.id) {
      this.printerService.update(this.printer.id, this.printer).subscribe(
        (res) => {
          this.messageService.add({
            severity: 'success',
            summary: 'Éxito',
            detail: 'Impresora Actualizada',
            life: 3000,
          });
          this.printers = [];
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
      this.printerService.create(this.printer).subscribe(
        (res) => {
          this.messageService.add({
            severity: 'success',
            summary: 'Éxito',
            detail: 'Impresora Creada',
            life: 3000,
          });
          this.printers = [];
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
      this.printerDialog = true;
      return;
    }

    this.printers = [...this.printers];
    this.printerDialog = false;
    this.printer = {};
  }

  hideDialog() {
    this.printerDialog = false;
    this.submitted = false;
  }

  getAll() {
    this.printerService.getAll().subscribe((printers) => {
      this.printers = printers;
      this.loading = false;
    });
  }

  isValidToSave(): boolean {
    return this.printer.name && this.printer.numColors && this.printer.description ? true : false;
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
