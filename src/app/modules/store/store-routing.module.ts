import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { StoreCebComponent } from './pages/store-ceb/store-ceb.component';
import { StoreCibComponent } from './pages/store-cib/store-cib.component';
import { StoreCreateComponent } from './pages/store-create/store-create.component';
import { StoreComponent } from './pages/store-list/store.component';
import { StoreMovComponent } from './pages/store-mov/store-mov.component';

const routes: Routes = [
  {
    path: '',
    component: StoreComponent,
  },
  {
    path: 'nuevo',
    component: StoreCreateComponent,
  },
  {
    path: 'CEB',
    component: StoreCebComponent,
  },
  {
    path: 'CIB',
    component: StoreCibComponent,
  },
  {
    path: 'MOV',
    component: StoreMovComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class StoreRoutingModule { }
