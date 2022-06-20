import { Component, OnInit } from '@angular/core';
import { TableColumn } from 'src/app/types/table.types';
import { CreateDieProduct, DieProduct } from 'src/app/types/die-product.types';
import { TABLE_REPORT_TEMPLATE } from 'src/app/core/constants/table';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { ConfirmationService, MenuItem } from 'primeng/api';
import { Status } from 'src/app/types/catalogs.types';
import { BreadcrumbService } from 'src/app/core/services/breadcrumb.service';
import { DieProductService } from 'src/app/core/http/die-product/die-product.service';
import { StatusService } from 'src/app/core/http/catalogs/status/status.service';
import { ToastService } from 'src/app/core/services/toast.service';
import { DIE_PRODUCT_STATUS_TYPE } from 'src/app/core/constants/status-types';
import {
  DieProductModalComponent
} from 'src/app/modules/dies/components/die-product-modal/die-product-modal.component';
import { Table } from 'primeng/table';

@Component({
  selector: 'app-die-product-list',
  templateUrl: './die-product-list.component.html',
  styleUrls: ['./die-product-list.component.scss'],
  providers: [
    ConfirmationService
  ]
})
export class DieProductListComponent implements OnInit {

  columns: TableColumn<DieProduct>[];
  pageSize: number = 10;
  dieProducts: DieProduct[];
  tableReportTemplate = TABLE_REPORT_TEMPLATE;
  rowsPerPageOptions: number[] = [5, 10, 20, 50, 100];
  addDialogRef: DynamicDialogRef;
  selectedDieProduct: DieProduct;
  menuItems: MenuItem[];
  dieProductStates: Status[] = [];

  constructor(
    private breadcrumbService: BreadcrumbService,
    private dieProductService: DieProductService,
    private statusService: StatusService,
    public dialogService: DialogService,
    private toastService: ToastService,
    private confirmationService: ConfirmationService
  ) {
    this.breadcrumbService.setItems([
      { label: 'Diseño' },
      { label: 'Troqueles' },
      { label: 'Productos troquelados', routerLink: ['/home/troqueles/productos-troquelados'] },
    ]);
  }

  ngOnInit(): void {
    this.getAllDieProducts();
    this.getMenuItems();
    this.getDieProductStates();
    this.columns = [
      {field: 'code', header: 'Ptroq', type: 'text'},
      {field: 'name', header: 'Nombre', type: 'text'},
      {field: 'area', header: 'Área', type: 'numeric'},
      {field: 'length', header: 'Largo (mm)', type: 'numeric'},
      {field: 'width', header: 'Ancho (mm)', type: 'numeric'},
      {field: 'gsmdis', header: 'Gsmdis', type: 'numeric'}
    ];
  }

  private getAllDieProducts() {
    this.dieProductService.getAllDieProducts()
      .subscribe(dieProducts => {
        this.dieProducts = dieProducts;
      });
  }

  private getDieProductStates(): void {
    this.statusService.getAllByType(DIE_PRODUCT_STATUS_TYPE)
      .subscribe(dieStates => {
        this.dieProductStates = dieStates;
      });
  }

  private getMenuItems(): void {
    this.menuItems = [
      {
        label: 'Editar',
        icon: 'pi pi-pencil',
        command: () => this.editDieProduct()
      },
      {
        label: 'Eliminar',
        icon: 'pi pi-trash',
        command: () => this.deleteDieProduct()
      }
    ];
  }

  addDieProduct(): void {
    this.addDialogRef = this.dialogService.open(DieProductModalComponent, {
      header: 'Crear nuevo producto troquelado',
      width: '450px',
      contentStyle: {'max-width': '100%', 'overflow': 'hidden'},
    });

    this.addDialogRef.onClose
      .subscribe((dieProduct: CreateDieProduct) => {
        if (dieProduct) {
          this.dieProductService.createDieProduct(dieProduct)
            .subscribe(() => {
              this.toastService.success('Producto troquelado creado correctamente');
              this.getAllDieProducts();
            });
        }
      });
  }

  editDieProduct(): void {
    this.addDialogRef = this.dialogService.open(DieProductModalComponent, {
      data: this.selectedDieProduct,
      header: `Actualizar ${this.selectedDieProduct.code}`,
      width: '450px',
      contentStyle: {'max-width': '100%', 'overflow': 'hidden'},
    });

    this.addDialogRef.onClose
      .subscribe((dieProduct) => {
        if (dieProduct) {
          this.dieProductService.updateDieProduct(this.selectedDieProduct.id, dieProduct)
            .subscribe(() => {
              this.toastService.success('Producto troquelado actualizado correctamente');
              this.getAllDieProducts()
            })
        }
      });
  }

  deleteDieProduct(): void {
    this.confirmationService.confirm({
      message:
        '¿Estas seguro de eliminar el producto troquelado ' + this.selectedDieProduct.code + '?',
      header: 'Confirmación',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.dieProductService.deleteDieProduct(this.selectedDieProduct.id)
          .subscribe(deleted => {
            if (deleted) {
              this.toastService.success('Producto troquelado eliminado correctamente');
              this.getAllDieProducts();
            } else {
              this.toastService.error('Error al eliminar el producto troquelado');
            }
          });
      },
    });
  }

  clear(table: Table) {
    table.clear();
  }
}
