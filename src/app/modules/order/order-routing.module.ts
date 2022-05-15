import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DiesListComponent } from 'src/app/modules/dies/pages/dies-list/dies-list.component';
import { OrderComponent } from './pages/order-list/order.component';

const routes: Routes = [
  {
    path: '',
    component: OrderComponent,
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class OrderRoutingModule { }
