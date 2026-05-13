import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { StoreCebComponent } from './pages/store-ceb/store-ceb.component';
import { StoreCepComponent } from './pages/store-cep/store-cep.component';
import { StoreCibComponent } from './pages/store-cib/store-cib.component';
import { StoreCreateComponent } from './pages/store-create/store-create.component';
import { StoreComponent } from './pages/store-list/store.component';
import { StoreMovComponent } from './pages/store-mov/store-mov.component';
import { StoreTm1Component } from './pages/store-tm1/store-tm1.component';
import { StoreTm5Component } from './pages/store-tm5/store-tm5.component';

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
  {
    path: 'CEP',
    component: StoreCepComponent,
  },
  {
    path: 'TM1',
    component: StoreTm1Component,
  },
  {
    path: 'TM5',
    component: StoreTm5Component,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class StoreRoutingModule { }
