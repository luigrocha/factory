import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CirelesListComponent } from './pages/cireles-list/cireles-list.component';
import { CirelesRoutingModule } from './cireles-routing.module';

@NgModule({
  imports: [
    CirelesRoutingModule,
    CommonModule
  ],
  declarations: [
    CirelesListComponent
  ]
})
export class CirelesModule { }
