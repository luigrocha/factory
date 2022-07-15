import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { Table } from 'primeng/table';
import { ConfirmationService, FilterMetadata, MenuItem } from 'primeng/api';
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
import { OrderStatus } from 'src/app/types/enums/order-status';
import { SearchRequest } from 'src/app/types/pageable.types';
import { PFilter } from 'src/app/types/filter.types';
import { getSearchCriteria } from 'src/app/core/utils/filter-table';
import { FormControl } from '@angular/forms';
import { debounceTime, distinctUntilChanged } from 'rxjs/operators';
import { TableColumn } from 'src/app/types/table.types';
import { checkIfOptionIsAllowed } from 'src/app/core/utils/permission';

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
  menuItems: MenuItem[] = [];
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

  permissions: TypePermission[];
  orderPriorityType = ORDER_PRIORITY_TYPE;
  searchFomControl = new FormControl();
  columns: TableColumn<Order>[];

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
    this.getPermissionsToPage();
    this.getOrders();
    this.getPriorities();
    this.getStatus();
    this.setDefaultFilters();
    this.searchByInput();
    this.columns = [
      { field: 'code', header: 'Código' },
      { field: 'lot', header: 'Lote' },
      { field: 'client.name', header: 'Cliente' },
      { field: 'productCode', header: 'Código producto' },
      { field: 'name', header: 'Nombre' },
      { field: 'quantity', header: 'Cantidad' },
      { field: 'estimatedDeliveryAt', header: 'Fecha Entrega' },
      { field: 'priority.name', header: 'Prioriodad' },
      { field: 'status.name', header: 'Estado' },
      { field: 'clientOrderCode', header: 'Orden' },
      { field: 'observation', header: 'Observaciones' },
      { field: 'pendingQuantity', header: 'Cantidad pendiente' },
      { field: 'shippedQuantity', header: 'Despachos' },
      { field: 'lastModifiedAt', header: 'Última modificación' }
    ];
  }

  setDefaultFilters(): void {
    this.table.filters = {
      ...this.table.filters,
      'status': [
        {
          value: this.searchRequest.filters,
          matchMode: 'in',
          operator: 'and'
        }]
    };
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
        const statusFilters: Array<FilterMetadata> = filters['status'];
        if (statusFilters && statusFilters.length > 0) {
          const selectedStates: string[] = statusFilters[0].value;
          if (selectedStates) {
            this.searchRequest.filters = selectedStates;
          } else {
            this.searchRequest.filters = [];
          }
        }
        this.buildSearchCriteria(filters);
        this.getOrders();
      });
  }

  buildSearchCriteria(filters: any): void {
    this.searchRequest.searchCriteria = [];
    let primeFilters: Array<string> = Object.keys(filters);
    primeFilters = primeFilters.filter(filter => filter !== 'status');
    const filterFields: Array<PFilter> = [];
    primeFilters.forEach(field => {
      const filter: PFilter = {
        key: field,
        values: filters[field]
      };
      filterFields.push(filter);
    });
    this.searchRequest.searchCriteria = getSearchCriteria(filterFields);
  }

  getPermissionsToPage() {
    this.permissionService.findPermissionPage()
      .subscribe(permissions => {
          this.permissions = permissions;
          this.getMenuItems();
        }
      );
  }

  isAllow(id: number): boolean {
    return checkIfOptionIsAllowed(this.permissions, id);
  }

  getMenuItems() {
    if (this.isAllow(PermissionEnum.READ)) {
      this.menuItems.push({
        label: 'Ver pedido',
        icon: 'pi pi-eye',
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

  private editOrder() {
    this.route.navigate(['/home/pedidos/' + this.selectedOrder.id + '/' + this.selectedOrder.code]);
  }

  private deleteOrder() {
  }

  createNewOrder() {
    this.route.navigate(['/home/pedidos/crear']);
  }

  deleteSelectedOrders() {
  }

  searchByInput() {
    this.searchFomControl.valueChanges
      .pipe(debounceTime(500), distinctUntilChanged())
      .subscribe(query => {
        this.searchRequest.query = query;
        this.getOrders();
      });
  }
}
