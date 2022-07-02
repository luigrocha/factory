import { Component, OnInit } from '@angular/core';
import { Table } from 'primeng/table';
import { ConfirmationService, MenuItem } from 'primeng/api';
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
import { TABLE_REPORT_TEMPLATE } from 'src/app/core/constants/table';
import { ToastService } from 'src/app/core/services/toast.service';
import { ORDER_STATUS_TYPE } from 'src/app/core/constants/status-types';

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.scss'],
  providers: [ConfirmationService],
})
export class OrderComponent implements OnInit {

  pageSize: number = 10;
  orders: Order[];
  tableReportTemplate = TABLE_REPORT_TEMPLATE;
  rowsPerPageOptions: number[] = [5, 10, 20, 50, 100];
  selectedOrder: Order;
  menuItems: MenuItem[];
  orderStatus: Status[] = [];
  priorities: Priority[] = [];
  selectedOrders: Order[] = [];

  globalFilterFields: string[] = [
    'lote',
    'client.name',
    'code',
    'name',
    'amount',
    'deliverAt',
    'observation',
    'difference',
    'priority',
    'status'
  ];
  permissionsPage: TypePermission[];
  orderStatusCode = ORDER_STATUS_TYPE;

  constructor(
    private toastService: ToastService,
    private confirmationService: ConfirmationService,
    private breadcrumbService: BreadcrumbService,
    private orderService: OrderService,
    private priorityService: PriorityService,
    private statusService: StatusService,
    private permissionService: PermissionService,
    private route: Router
  ) {
    this.breadcrumbService.setItems([
      {label: 'Pedidos'},
      {label: 'Gestión de Pedidos', routerLink: ['/home/pedidos']},
    ]);
  }

  ngOnInit() {
    this.getPermissionsPage();
    this.getOrders();
    this.getPriorities();
    this.getStatus();
    // setTimeout(() => {
    //   this.getMenuItems();
    // }, 500);
    // this.cols = [
    //   { field: 'lote', header: 'Lote' },
    //   { field: 'client', header: 'Cliente' },
    //   { field: 'code', header: 'Codigo' },
    //   { field: 'name', header: 'Nombre' },
    //   { field: 'amount', header: 'Cantidad' },
    //   { field: 'deliverAt', header: 'Fecha Entrega' },
    //   { field: 'observation', header: 'Observación' },
    //   { field: 'difference', header: 'Diferencia' },
    //   { field: 'priority', header: 'Prioriodad' },
    //   { field: 'status', header: 'Estado' },
    // ];
  }

  getMenuItems() {
    if (this.isAllow(PermissionEnum.UPDATE)) {
      this.menuItems.push({
        label: 'Editar',
        icon: 'pi pi-pencil',
        command: () => this.editOrder()
      });
    }
    if (this.isAllow(PermissionEnum.DELETE)) {
      this.menuItems.push({
        label: 'Eliminar',
        icon: 'pi pi-trash',
        command: () => this.deleteOrder()
      });
    }
    this.menuItems.push({
      label: 'Ver Estado',
      icon: 'pi pi-search-plus',
      command: () => this.route.navigate(['/home/pedidos/status/' + this.selectedOrder.lot])
    });

  }

  getOrders() {
    this.priorities = [];
    this.orderService.getAll()
      .subscribe(orders => {
        this.orders = orders;
      });
  }

  getPriorities() {
    this.priorityService.getAllByType(this.orderStatusCode)
      .subscribe((priorities) => {
        this.priorities = priorities;
      });
  }

  getStatus() {
    this.statusService.getAllByType('P')
      .subscribe((status) => {
        this.orderStatus = status;
      });
  }

  clear(table: Table) {
    table.clear();
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

  private editOrder() {
    this.route.navigate(['/home/pedidos/editar/' + this.selectedOrder.lot]);
  }

  private deleteOrder() {
  }

  createNewOrder() {
    this.route.navigate(['/home/pedidos/crear']);
  }

  deleteSelectedOrders() {
  }
}
