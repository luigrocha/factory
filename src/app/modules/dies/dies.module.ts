import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DiesRoutingModule } from './dies-routing.module';
import { DiesListComponent } from './pages/dies-list/dies-list.component';


@NgModule({
  declarations: [
    DiesListComponent
  ],
  imports: [
    CommonModule,
    DiesRoutingModule
  ]
})
export class DiesModule { }
