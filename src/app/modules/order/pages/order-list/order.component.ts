import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
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
import { ORDER_PRIORITY_TYPE } from 'src/app/core/constants/priority-type';
import { debounceTime } from 'rxjs/operators';
import { OrderStatus } from 'src/app/types/enums/order-status';
import { SearchCriteria, SearchRequest } from 'src/app/types/pageable.types';
import { PFilterElement } from "../../../../types/filter.types";

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.scss'],
  providers: [ConfirmationService],
})
export class OrderComponent implements OnInit, AfterViewInit {

  orders: Order[];
  tableReportTemplate = TABLE_REPORT_TEMPLATE;
  rowsPerPageOptions: number[] = [5, 10, 20, 50, 100];
  selectedOrder: Order;
  menuItems: MenuItem[];
  orderStatus: Status[] = [];
  priorities: Priority[] = [];
  selectedOrders: Order[] = [];
  searchRequest: SearchRequest = {
    page: 0,
    size: 20,
    filters: [
      OrderStatus.PENDING,
      OrderStatus.IN_PROGRESS,
      OrderStatus.DONE,
      OrderStatus.TO_START
    ],
    query: '',
    searchCriteria: []
  };

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
  orderPriorityType = ORDER_PRIORITY_TYPE;

  @ViewChild('dt', {static: true}) table: Table;

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

  ngAfterViewInit() {
    this.table.onPage
      .subscribe(({first, rows}) => {
        this.searchRequest.page = first / rows;
        this.searchRequest.size = rows;
        this.getOrders();
      });
    this.table.onFilter
      .subscribe(({filters}) => {
        const statusFilters: Array<PFilterElement> = filters['status'];
        if (statusFilters && statusFilters.length > 0) {
          const selectedStates: string[] = statusFilters[0].value;
          if (selectedStates) {
            this.searchRequest.filters = selectedStates;
          }
        } else {
          this.buildSearchCriteria(filters);
        }
        this.getOrders();
      });
  }

  buildSearchCriteria(filters: any): void {
    this.searchRequest.searchCriteria = [];
    // Get objects keys from filters except global filter fields and status
    const keys = Object.keys(filters).filter(key => !this.globalFilterFields.includes(key));
    let primeFilters: Array<string> = Object.keys(filters);
    // Remove status from prime filters
    primeFilters = primeFilters.filter(filter => filter !== 'status');

    const filterFields: Array<Array<PFilterElement>> = [];
    primeFilters.forEach(field => {
      filterFields.push(filters[field]);
    });
    console.log(filterFields);
    const clientNameFilters: Array<PFilterElement> = filters['client.name'];
    const codeFilters: Array<PFilterElement> = filters['code'];
    const estimatedDeliveryDateFilters: Array<PFilterElement> = filters['estimatedDeliveryAt'];
    const lotFilters: Array<PFilterElement> = filters['lot'];
    const nameFilters: Array<PFilterElement> = filters['name'];
    const priorityNameFilters: Array<PFilterElement> = filters['priority.name'];
    const productCodeFilters: Array<PFilterElement> = filters['productCode'];
    const statusFilters: Array<PFilterElement> = filters['status'];
    console.log(clientNameFilters);
    console.log(primeFilters);
  }

  // getSearchCriteria(filters: ): SearchCriteria[] {
  // }

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
    this.orderService.getOrders(this.searchRequest)
      .subscribe(orders => {
        this.orders = orders.content;
        this.table.totalRecords = orders.totalElements;
      });
  }

  getPriorities() {
    this.priorityService.getAllByType(this.orderPriorityType)
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

  searchByStates(value) {
    console.log(value);
  }
}
