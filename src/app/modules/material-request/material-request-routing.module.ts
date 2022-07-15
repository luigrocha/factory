import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {
  MaterialRequestListComponent
} from 'src/app/modules/material-request/pages/material-request-list/material-request-list.component';
import {
  MaterialRequestFormComponent
} from 'src/app/modules/material-request/pages/material-request-form/material-request-form.component';

const routes: Routes = [
  {
    path: '',
    component: MaterialRequestListComponent
  },
  {
    path: 'form/:id',
    component: MaterialRequestFormComponent
  },
  {
    path: 'form',
    component: MaterialRequestFormComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MaterialRequestRoutingModule { }
