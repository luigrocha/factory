import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DiesRoutingModule } from './dies-routing.module';
import { DiesListComponent } from './pages/dies-list/dies-list.component';
import { TableModule } from 'primeng/table';
import { InputTextModule } from 'primeng/inputtext';


@NgModule({
  declarations: [
    DiesListComponent
  ],
  imports: [
    CommonModule,
    DiesRoutingModule,
    TableModule,
    InputTextModule
  ]
})
export class DiesModule { }
