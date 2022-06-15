import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DiesRoutingModule } from './dies-routing.module';
import { DiesListComponent } from './pages/dies-list/dies-list.component';
import { TableModule } from 'primeng/table';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { RippleModule } from 'primeng/ripple';
import { SharedModule } from 'src/app/shared/shared.module';
import { DieProductListComponent } from './pages/die-product-list/die-product-list.component';
import { ToastModule } from "primeng/toast";
import { TooltipModule } from "primeng/tooltip";
import { ConfirmDialogModule } from "primeng/confirmdialog";
import { MenuModule } from "primeng/menu";
import { DropdownModule } from "primeng/dropdown";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { DieProductModalComponent } from './components/die-product-modal/die-product-modal.component';
import { DialogService } from "primeng/dynamicdialog";
import { CreateDieComponent } from './pages/create-die/create-die.component';
import { InputTextareaModule } from "primeng/inputtextarea";
import { DividerModule } from "primeng/divider";
import { MultiSelectModule } from "primeng/multiselect";


@NgModule({
  declarations: [
    DiesListComponent,
    DieProductListComponent,
    DieProductModalComponent,
    CreateDieComponent
  ],
  imports: [
    CommonModule,
    DiesRoutingModule,
    TableModule,
    InputTextModule,
    ButtonModule,
    RippleModule,
    SharedModule,
    ToastModule,
    TooltipModule,
    ConfirmDialogModule,
    MenuModule,
    DropdownModule,
    FormsModule,
    ReactiveFormsModule,
    InputTextareaModule,
    DividerModule,
    MultiSelectModule
  ],
  providers: [
    DialogService
  ]
})
export class DiesModule { }
