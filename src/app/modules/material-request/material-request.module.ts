import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MaterialRequestRoutingModule } from './material-request-routing.module';
import { MaterialRequestListComponent } from './pages/material-request-list/material-request-list.component';
import { MaterialRequestFormComponent } from './pages/material-request-form/material-request-form.component';
import { ToastModule } from 'primeng/toast';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { RippleModule } from 'primeng/ripple';
import { InputTextModule } from 'primeng/inputtext';
import { StepsModule } from 'primeng/steps';


@NgModule({
  declarations: [
    MaterialRequestListComponent,
    MaterialRequestFormComponent
  ],
  imports: [
    CommonModule,
    MaterialRequestRoutingModule,
    ToastModule,
    TableModule,
    ButtonModule,
    RippleModule,
    InputTextModule,
    StepsModule
  ]
})
export class MaterialRequestModule { }
