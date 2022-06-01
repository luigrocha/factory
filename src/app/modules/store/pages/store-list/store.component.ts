import { Component, OnInit } from '@angular/core';
import { ConfirmationService, MessageService } from 'primeng/api';
import { CellerService } from 'src/app/core/http/celler/celler.service';
import { BreadcrumbService } from 'src/app/core/services/breadcrumb.service';
import { Celler } from 'src/app/types/celler.types';

@Component({
  selector: 'app-store',
  templateUrl: './store.component.html',
  styleUrls: ['./store.component.scss'],
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
export class StoreComponent implements OnInit {

  cellerDialog: boolean;

  selectedCeller: Celler[];

  submitted: boolean;

  cols: any[];

  cellers: Celler[];

  celler: Celler;

  loading = true;

  constructor(
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private breadcrumbService: BreadcrumbService,
    private cellerService: CellerService,
  ) {
    this.breadcrumbService.setItems([
      { label: 'Pedidos' },
      { label: 'Gestión de bodega', routerLink: ['home/pedidos'] },
    ]);
  }

  ngOnInit() {
    this.getAll();
    this.cols = [
      { field: 'lote', header: 'Lote' },
      { field: 'amount', header: 'Cantidad' },
      { field: 'balance', header: 'Saldos' },
      { field: 'coat', header: 'Sacos' },
      { field: 'pallets', header: 'Pallets' },
      { field: 'weight', header: 'Peso Kg' },
      { field: 'date', header: 'Fecha' },
      { field: 'observation', header: 'Observación' },
      { field: 'material', header: 'Producto' },
      { field: 'location', header: 'Ubicación' },
      { field: 'document', header: 'Documento' },
    ];
  }

  // openNew() {
  //   this.order = {};
  //   this.submitted = false;
  //   this.orderDialog = true;
  // }

  // editOrder(order: Order) {
  //   this.order = { ...order };
  //   this.orderDialog = true;
  // }

  // deleteOrder(order: Order) {
  //   this.confirmationService.confirm({
  //     message:
  //       'Estas seguro de eliminar el order ' + order.lote + '?',
  //     header: 'Confirmación',
  //     icon: 'pi pi-exclamation-triangle',
  //     accept: () => {
  //       // this.orderService.delete(order.id).subscribe(
  //       //   (res) => {
  //       //     this.messageService.add({
  //       //       severity: 'success',
  //       //       summary: 'Éxito',
  //       //       detail: 'Homopolímero Eliminado',
  //       //       life: 3000,
  //       //     });
  //       //     this.orders = [];
  //       //     this.getAllUsers();
  //       //   },
  //       //   (err) => {
  //       //     this.messageService.add({
  //       //       severity: 'error',
  //       //       summary: 'Error',
  //       //       detail: err.error,
  //       //       life: 3000,
  //       //     });
  //       //   }
  //       // );

  //     },
  //   });
  // }

  // deleteSelectedOrders() {
  //   this.confirmationService.confirm({
  //     message: 'Estás seguro de eliminar los orderes seleccionados?',
  //     header: 'Confirmación',
  //     icon: 'pi pi-exclamation-triangle',
  //     accept: () => {
  //       // this.selectedHomo.forEach(order => {
  //       //   this.orderService.delete(order.id).subscribe(
  //       //     (res) => {
  //       //       this.messageService.add({
  //       //         severity: 'success',
  //       //         summary: 'Éxito',
  //       //         detail: 'Homopolímero Eliminado',
  //       //         life: 3000,
  //       //       });
  //       //       this.orders = [];
  //       //       this.getAllUsers();
  //       //     },
  //       //     (err) => {
  //       //       this.messageService.add({
  //       //         severity: 'error',
  //       //         summary: 'Error',
  //       //         detail: err.error,
  //       //         life: 3000,
  //       //       });
  //       //     }
  //       //   );
  //       // })

  //       this.selectedOrder = null;
  //       this.messageService.add({
  //         severity: 'success',
  //         summary: 'Correcto',
  //         detail: 'Homopolímeros Elimnados',
  //         life: 3000,
  //       });
  //     },
  //   });
  // }

  // saveOrder() {
  //   this.submitted = true;

  //   if (this.order.id) {
  //     // this.orderService.update(this.order.id, this.order).subscribe(
  //     //   (res) => {
  //     //     this.messageService.add({
  //     //       severity: 'success',
  //     //       summary: 'Éxito',
  //     //       detail: 'Homopolímero Actualizado',
  //     //       life: 3000,
  //     //     });
  //     //     this.orders = [];
  //     //     this.getAllUsers();
  //     //   },
  //     //   (err) => {
  //     //     this.messageService.add({
  //     //       severity: 'error',
  //     //       summary: 'Error',
  //     //       detail: err.error,
  //     //       life: 3000,
  //     //     });
  //     //   }
  //     // );
  //   } else {
  //     // this.orderService.create(this.order).subscribe(
  //     //   (res) => {
  //     //     this.messageService.add({
  //     //       severity: 'success',
  //     //       summary: 'Éxito',
  //     //       detail: 'Homopolímero Creado',
  //     //       life: 3000,
  //     //     });
  //     //     this.orders = [];
  //     //     this.getAllUsers();
  //     //   },
  //     //   (err) => {
  //     //     this.messageService.add({
  //     //       severity: 'error',
  //     //       summary: 'Error',
  //     //       detail: err.error,
  //     //       life: 3000,
  //     //     });
  //     //   }
  //     // );
  //   }

  //   this.orders = [...this.orders];
  //   this.orderDialog = false;
  //   this.order = {};
  // }

  hideDialog() {
    this.cellerDialog = false;
    this.submitted = false;
  }

  getAll() {
    this.cellerService.getAll().subscribe((cellers) => {
      this.cellers = cellers;
      this.loading = false;
    });
  }

}
