import {Component, OnInit} from '@angular/core';
import {BreadcrumbService} from 'src/app/core/services/breadcrumb.service';
import {OrderService} from '../../../../core/http/orders/order.service';
import {Order} from '../../../../types/order.types';
import {OrderStatus} from '../../../../types/enums/order-status';

@Component({
  selector: 'app-mixture-list',
  templateUrl: './mixture-list.component.html',
  styleUrls: ['./mixture-list.component.scss']
})
export class MixtureListComponent implements OnInit {

  orders: Order[];

  constructor(
    private breadcrumbService: BreadcrumbService,
    private orderService: OrderService,
  ) {
    this.breadcrumbService.setItems([
      {label: 'Módulo de Mezcla'},
      {label: 'Diseño de Mezcla', routerLink: ['/home/mezcla']},
    ]);
  }

  ngOnInit() {
    this.getOrdersByStatus();
  }

  getOrdersByStatus() {
    this.orderService.getOrdersByStatus(OrderStatus.PENDING).subscribe(orders => {
      this.orders = orders;
    });
  }

}
