import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { StoreCreateComponent } from './pages/store-create/store-create.component';
import { StoreComponent } from './pages/store-list/store.component';

const routes: Routes = [
  {
    path: '',
    component: StoreComponent,
  },
  {
    path: 'nuevo',
    component: StoreCreateComponent,
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class StoreRoutingModule { }
