import { Component, OnInit } from '@angular/core';
import { BreadcrumbService } from 'src/app/core/services/breadcrumb.service';
import { PrimeIcons } from 'primeng/api';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-order-status',
  templateUrl: './order-status.component.html',
  styleUrls: ['./order-status.component.scss']
})
export class OrderStatusComponent implements OnInit {

  customEvents: any[];
  loteId: string;

  constructor(
    private breadcrumbService: BreadcrumbService,
    private activatedRoute: ActivatedRoute,) {
    this.breadcrumbService.setItems([
      { label: 'Pedidos' },
      { label: 'Gestión de Pedios', routerLink: ['pedidos'] },
      { label: 'Status Pedido' },
    ]);
  }

  ngOnInit() {
    this.loteId = this.activatedRoute.snapshot.params.lote;
    this.customEvents = [
      {
        status: 'Ordered',
        date: '15/10/2020 10:30',
        icon: PrimeIcons.SHOPPING_CART,
        color: '#9C27B0',
        image: 'game-controller.jpg'
      },
      { status: 'Processing', date: '15/10/2020 14:00', icon: PrimeIcons.COG, color: '#673AB7' },
      { status: 'Shipped', date: '15/10/2020 16:15', icon: PrimeIcons.ENVELOPE, color: '#FF9800' },
      { status: 'Delivered', date: '16/10/2020 10:00', icon: PrimeIcons.CHECK, color: '#607D8B' }
    ];

  }

}
