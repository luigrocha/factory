import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MixtureListComponent } from './pages/mixture-list/mixture-list.component';
import { MixtureRoutingModule } from './mixture-routing.module';
import { ToastModule } from 'primeng/toast';
import { ButtonModule } from 'primeng/button';
import { DividerModule } from 'primeng/divider';
import { AutoCompleteModule } from 'primeng/autocomplete';
import { FormsModule } from '@angular/forms';
import { InputTextModule } from 'primeng/inputtext';
import { CreateMixtureComponent } from './pages/create-mixture/create-mixture.component';
import { SidebarModule } from 'primeng/sidebar';
import {TooltipModule} from 'primeng/tooltip';

@NgModule({
  imports: [
    CommonModule,
    MixtureRoutingModule,
    ToastModule,
    ButtonModule,
    DividerModule,
    AutoCompleteModule,
    FormsModule,
    InputTextModule,
    TooltipModule,
    SidebarModule
  ],
  declarations: [
    MixtureListComponent,
    CreateMixtureComponent,
  ]
})
export class MixtureModule { }
