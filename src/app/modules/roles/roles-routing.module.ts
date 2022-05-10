import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RolesListComponent } from 'src/app/modules/roles/pages/roles-list/roles-list.component';

const routes: Routes = [
  {
    path: '',
    component: RolesListComponent,
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class RolesRoutingModule { }
