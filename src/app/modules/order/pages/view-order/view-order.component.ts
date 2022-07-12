import { Component, OnInit } from '@angular/core';
import { BreadcrumbService } from 'src/app/core/services/breadcrumb.service';
import { ActivatedRoute } from '@angular/router';
import { MenuItem } from 'primeng/api';
import { OrderService } from 'src/app/core/http/orders/order.service';

@Component({
  selector: 'app-view-order',
  templateUrl: './view-order.component.html',
  styleUrls: ['./view-order.component.scss']
})
export class ViewOrderComponent implements OnInit {
  menuItems: MenuItem[];
  tabIndex: number = 0;

  constructor(
    private breadcrumbService: BreadcrumbService,
    private activatedRoute: ActivatedRoute,
    private orderService: OrderService
  ) {
    const orderId = this.activatedRoute.snapshot.params.id;
    this.breadcrumbService.setItems([
      {label: 'Pedidos'},
      {label: 'Gestión de Pedidos', routerLink: ['/home/pedidos']},
      {label: `Pedido (${orderId})`, routerLink: ['/home/pedidos/' + orderId]}
    ]);
  }

  ngOnInit(): void {
    this.getMenuItems();
    this.activatedRoute.paramMap
      .subscribe(params => {
        const orderId = params.get('id');
        this.orderService.getOrder(Number(orderId))
          .subscribe(order => {
            console.log(order);
          });
      })
  }

  getMenuItems(): void {
    this.menuItems = [
      {
        label: 'Cancelar pedido',
        icon: 'pi pi-fw pi-user'
      },
    ];
  }
}
