import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OrderComponent } from './pages/order-list/order.component';
import { OrderRoutingModule } from './order-routing.module';
import { ToastModule } from 'primeng/toast';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { RippleModule } from 'primeng/ripple';
import { InputTextModule } from 'primeng/inputtext';
import { TooltipModule } from 'primeng/tooltip';
import { TagModule } from 'primeng/tag';
import { DialogModule } from 'primeng/dialog';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { OrderStatusComponent } from './pages/view-order/order-status/order-status.component';
import { TimelineModule } from 'primeng/timeline';
import { DropdownModule } from 'primeng/dropdown';
import { MenuModule } from 'primeng/menu';
import { SharedModule } from 'src/app/shared/shared.module';
import { MultiSelectModule } from 'primeng/multiselect';
import { CreateOrderComponent } from './pages/create-order/create-order.component';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { CalendarModule } from 'primeng/calendar';
import { ViewOrderComponent } from './pages/view-order/view-order.component';
import { CardModule } from 'primeng/card';
import { TabViewModule } from 'primeng/tabview';
import { StyleClassModule } from 'primeng/styleclass';
import { DividerModule } from 'primeng/divider';
import { EditOrderFormComponent } from './pages/view-order/edit-order-form/edit-order-form.component';
import { MessageModule } from 'primeng/message';
import { ConfirmationService } from 'primeng/api';
import { DialogService } from 'primeng/dynamicdialog';

@NgModule({
  imports: [
    CommonModule,
    OrderRoutingModule,
    ToastModule,
    TableModule,
    ButtonModule,
    RippleModule,
    InputTextModule,
    TooltipModule,
    TagModule,
    DialogModule,
    FormsModule,
    ConfirmDialogModule,
    TimelineModule,
    DropdownModule,
    MenuModule,
    SharedModule,
    MultiSelectModule,
    ReactiveFormsModule,
    InputTextareaModule,
    CalendarModule,
    CardModule,
    TabViewModule,
    StyleClassModule,
    DividerModule,
    MessageModule,
  ],
  declarations: [
    OrderComponent,
    OrderStatusComponent,
    CreateOrderComponent,
    ViewOrderComponent,
    EditOrderFormComponent,
  ],
  providers: [
    ConfirmationService,
    DialogService
  ]
})
export class OrderModule { }
