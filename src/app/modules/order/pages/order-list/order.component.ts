import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Table } from 'primeng/table';
import { ConfirmationService, MessageService } from 'primeng/api';
import { PriorityService } from 'src/app/core/http/catalogs/priority/priority.service';
import { StatusService } from 'src/app/core/http/catalogs/status/status.service';
import { OrderService } from 'src/app/core/http/orders/order.service';
import { BreadcrumbService } from 'src/app/core/services/breadcrumb.service';
import { Priority, Status } from 'src/app/types/catalogs.types';
import { Order } from 'src/app/types/order.types';

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.scss'],
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
export class OrderComponent implements OnInit {

  orderDialog: boolean;

  selectedOrder: Order[];

  submitted: boolean;

  cols: any[];

  orders: Order[];

  order: Order;

  loading = true;

  priorities: Priority[];

  status: Status[];

  @ViewChild('dt') table: Table;

  @ViewChild('filter') filter: ElementRef;

  constructor(
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private breadcrumbService: BreadcrumbService,
    private orderService: OrderService,
    private priorityService: PriorityService,
    private statusService: StatusService,
  ) {
    this.breadcrumbService.setItems([
      { label: 'Pedidos' },
      { label: 'Gestión de Pedidos', routerLink: ['home/pedidos'] },
    ]);
  }

  ngOnInit() {
    this.getAll();
    this.getPriorities();
    this.getStatus();
    this.cols = [
      { field: 'lote', header: 'Lote' },
      { field: 'client', header: 'Cliente' },
      { field: 'code', header: 'Codigo' },
      { field: 'name', header: 'Nombre' },
      { field: 'amount', header: 'Cantidad' },
      { field: 'deliverAt', header: 'Fecha Entrega' },
      { field: 'observation', header: 'Observación' },
      { field: 'difference', header: 'Diferencia' },
      { field: 'priority', header: 'Prioriodad' },
      { field: 'status', header: 'Estado' },
    ];
  }

  openNew() {
    this.order = {};
    this.submitted = false;
    this.orderDialog = true;
  }

  editOrder(order: Order) {
    this.order = { ...order };
    this.orderDialog = true;
  }

  deleteOrder(order: Order) {
    this.confirmationService.confirm({
      message:
        'Estas seguro de eliminar el order ' + order.lote + '?',
      header: 'Confirmación',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        // this.orderService.delete(order.id).subscribe(
        //   (res) => {
        //     this.messageService.add({
        //       severity: 'success',
        //       summary: 'Éxito',
        //       detail: 'Homopolímero Eliminado',
        //       life: 3000,
        //     });
        //     this.orders = [];
        //     this.getAllUsers();
        //   },
        //   (err) => {
        //     this.messageService.add({
        //       severity: 'error',
        //       summary: 'Error',
        //       detail: err.message,
        //       life: 3000,
        //     });
        //   }
        // );

      },
    });
  }

  deleteSelectedOrders() {
    this.confirmationService.confirm({
      message: 'Estás seguro de eliminar los orderes seleccionados?',
      header: 'Confirmación',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        // this.selectedHomo.forEach(order => {
        //   this.orderService.delete(order.id).subscribe(
        //     (res) => {
        //       this.messageService.add({
        //         severity: 'success',
        //         summary: 'Éxito',
        //         detail: 'Homopolímero Eliminado',
        //         life: 3000,
        //       });
        //       this.orders = [];
        //       this.getAllUsers();
        //     },
        //     (err) => {
        //       this.messageService.add({
        //         severity: 'error',
        //         summary: 'Error',
        //         detail: err.message,
        //         life: 3000,
        //       });
        //     }
        //   );
        // })

        this.selectedOrder = null;
        this.messageService.add({
          severity: 'success',
          summary: 'Correcto',
          detail: 'Homopolímeros Elimnados',
          life: 3000,
        });
      },
    });
  }

  saveOrder() {
    this.submitted = true;

    if (this.order.id) {
      // this.orderService.update(this.order.id, this.order).subscribe(
      //   (res) => {
      //     this.messageService.add({
      //       severity: 'success',
      //       summary: 'Éxito',
      //       detail: 'Homopolímero Actualizado',
      //       life: 3000,
      //     });
      //     this.orders = [];
      //     this.getAllUsers();
      //   },
      //   (err) => {
      //     this.messageService.add({
      //       severity: 'error',
      //       summary: 'Error',
      //       detail: err.message,
      //       life: 3000,
      //     });
      //   }
      // );
    } else {
      // this.orderService.create(this.order).subscribe(
      //   (res) => {
      //     this.messageService.add({
      //       severity: 'success',
      //       summary: 'Éxito',
      //       detail: 'Homopolímero Creado',
      //       life: 3000,
      //     });
      //     this.orders = [];
      //     this.getAllUsers();
      //   },
      //   (err) => {
      //     this.messageService.add({
      //       severity: 'error',
      //       summary: 'Error',
      //       detail: err.message,
      //       life: 3000,
      //     });
      //   }
      // );
    }

    this.orders = [...this.orders];
    this.orderDialog = false;
    this.order = {};
  }

  hideDialog() {
    this.orderDialog = false;
    this.submitted = false;
  }

  getAll() {
    this.priorities = [];
    this.orderService.getAll().subscribe((orders) => {
      this.orders = orders;
      this.orders.forEach(order => order.deliverAt = new Date(order.deliverAt));
      this.loading = false;
    });
  }

  getPriorities() {
    this.priorityService.getAllByType('P').subscribe((priorities) => {
      this.priorities = priorities;
    });
  }

  getStatus() {
    this.statusService.getAllByType('P').subscribe((status) => {
      this.status = status;
    });
  }

  clear(table: Table) {
    table.clear();
    this.filter.nativeElement.value = '';
  }


}
