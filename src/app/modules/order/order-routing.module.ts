import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { OrderComponent } from './pages/order-list/order.component';
import { OrderStatusComponent } from './pages/order-status/order-status.component';
import { CreateOrderComponent } from 'src/app/modules/order/pages/create-order/create-order.component';

const routes: Routes = [
  {
    path: '',
    component: OrderComponent,
  },
  {
    path: 'crear',
    component: CreateOrderComponent,
  },
  {
    path: 'status/:lote',
    component: OrderStatusComponent,
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class OrderRoutingModule {
}
