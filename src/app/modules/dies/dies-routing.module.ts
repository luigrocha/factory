import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DiesListComponent } from 'src/app/modules/dies/pages/dies-list/dies-list.component';
import { DieProductListComponent } from "./pages/die-product-list/die-product-list.component";
import { CreateDieComponent } from 'src/app/modules/dies/pages/create-die/create-die.component';

const routes: Routes = [
  {
    path: '',
    component: DiesListComponent,
  },
  {
    path: 'crear',
    component: CreateDieComponent,
  },
  {
    path: 'productos-troquelados',
    component: DieProductListComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DiesRoutingModule { }
