import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DiesRoutingModule } from './dies-routing.module';
import { DiesListComponent } from './pages/dies-list/dies-list.component';
import { TableModule } from 'primeng/table';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { RippleModule } from 'primeng/ripple';
import { DieStatusHighlightDirective } from './directives/die-status-highlight/die-status-highlight.directive';


@NgModule({
  declarations: [
    DiesListComponent,
    DieStatusHighlightDirective
  ],
  imports: [
    CommonModule,
    DiesRoutingModule,
    TableModule,
    InputTextModule,
    ButtonModule,
    RippleModule
  ]
})
export class DiesModule { }
