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
import { FormsModule } from '@angular/forms';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { OrderStatusComponent } from './pages/order-status/order-status.component';
import { TimelineModule } from 'primeng/timeline';
import { DropdownModule } from 'primeng/dropdown';
import { MenuModule } from 'primeng/menu';

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
  ],
  declarations: [
    OrderComponent,
    OrderStatusComponent,
  ]
})
export class OrderModule { }
