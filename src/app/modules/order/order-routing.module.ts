import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DiesListComponent } from 'src/app/modules/dies/pages/dies-list/dies-list.component';
import { OrderComponent } from './pages/order-list/order.component';
import { OrderStatusComponent } from './pages/order-status/order-status.component';

const routes: Routes = [
  {
    path: '',
    component: OrderComponent,
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
export class OrderRoutingModule { }
