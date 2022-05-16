import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CirelesListComponent } from './pages/cireles-list/cireles-list.component';

const routes: Routes = [
  {
    path: '',
    component: CirelesListComponent,
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CirelesRoutingModule { }
