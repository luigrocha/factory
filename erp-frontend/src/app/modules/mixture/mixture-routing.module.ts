import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CreateMixtureComponent } from './pages/create-mixture/create-mixture.component';
import { MixtureListComponent } from './pages/mixture-list/mixture-list.component';

const routes: Routes = [
  {
    path: '',
    component: MixtureListComponent,
  },
  {
    path: 'crear/:lote/:num',
    component: CreateMixtureComponent,
  },

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MixtureRoutingModule {
}
