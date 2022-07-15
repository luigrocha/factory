import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { OrderComponent } from './pages/order-list/order.component';
import { CreateOrderComponent } from 'src/app/modules/order/pages/create-order/create-order.component';
import { ViewOrderComponent } from 'src/app/modules/order/pages/view-order/view-order.component';

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
    path: ':id/:code',
    component: ViewOrderComponent,
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class OrderRoutingModule {
}
