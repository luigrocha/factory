import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MixtureListComponent } from './pages/mixture-list/mixture-list.component';

const routes: Routes = [
  {
    path: '',
    component: MixtureListComponent,
  },

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MixtureRoutingModule {
}
