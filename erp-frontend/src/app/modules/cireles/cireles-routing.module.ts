import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CirelesListComponent } from './pages/cireles-list/cireles-list.component';
import { CreateCirelComponent } from 'src/app/modules/cireles/pages/create-cirel/create-cirel.component';

const routes: Routes = [
  {
    path: '',
    component: CirelesListComponent,
  },
  {
    path: 'crear',
    component: CreateCirelComponent,
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CirelesRoutingModule { }
