import { Component, OnInit } from '@angular/core';
import { AppComponent } from 'src/app/app.component';
import { BreadcrumbService } from 'src/app/core/services/breadcrumb.service';

@Component({
  selector: 'app-store-create',
  templateUrl: './store-create.component.html',
  styleUrls: ['./store-create.component.scss']
})
export class StoreCreateComponent implements OnInit {

  currentDate: Date;

  constructor(
    public app: AppComponent,
    private breadcrumbService: BreadcrumbService,
  ) {
    this.breadcrumbService.setItems([
      { label: 'Pedidos' },
      { label: 'Gestión de Pedios', routerLink: ['bodega'] },
      { label: 'Nuevo Pedido', routerLink: ['bodega/nuevo'] },
    ]);
  }

  ngOnInit() {
    this.currentDate = new Date();
  }

  print() {
    window.print();
  }

}
