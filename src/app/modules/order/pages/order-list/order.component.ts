import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Table } from 'primeng/table';
import { ConfirmationService, MenuItem, MessageService } from 'primeng/api';
import { PriorityService } from 'src/app/core/http/catalogs/priority/priority.service';
import { StatusService } from 'src/app/core/http/catalogs/status/status.service';
import { OrderService } from 'src/app/core/http/orders/order.service';
import { BreadcrumbService } from 'src/app/core/services/breadcrumb.service';
import { Priority, Status } from 'src/app/types/catalogs.types';
import { Order } from 'src/app/types/order.types';
import { TypePermission } from 'src/app/types/permission';
import { PermissionService } from 'src/app/core/http/permissions/permission.service';
import { PermissionEnum } from 'src/app/core/constants/permisions';
import { Router } from '@angular/router';

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

  permissionsPage: TypePermission[];

  items: MenuItem[] = [];

  orderSelect: Order;

  constructor(
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private breadcrumbService: BreadcrumbService,
    private orderService: OrderService,
    private priorityService: PriorityService,
    private statusService: StatusService,
    private permissionService: PermissionService,
    private route: Router
  ) {
    this.breadcrumbService.setItems([
      { label: 'Pedidos' },
      { label: 'Gestión de Pedidos', routerLink: ['pedidos'] },
    ]);
  }

  ngOnInit() {
    this.getPermissionsPage();
    this.getAll();
    this.getPriorities();
    this.getStatus();
    setTimeout(() => {
      this.getMenuItems();
    }, 500);
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

  getMenuItems() {
    if (this.isAllow(PermissionEnum.UPDATE)) {
      this.items.push({
        label: 'Editar',
        icon: 'pi pi-pencil',
        command: (e) => this.editOrder(this.orderSelect)
      });
    }
    if (this.isAllow(PermissionEnum.DELETE)) {
      this.items.push({
        label: 'Eliminar',
        icon: 'pi pi-trash',
        command: (e) => this.deleteOrder(this.orderSelect)
      });
    }
    this.items.push({
      label: 'Ver Estado',
      icon: 'pi pi-search-plus',
      command: (e) => this.route.navigate(['/home/pedidos/status/' + this.orderSelect.lote])
    });

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
        //       detail: err.error,
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
        //         detail: err.error,
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
      //       detail: err.error,
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
      //       detail: err.error,
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
