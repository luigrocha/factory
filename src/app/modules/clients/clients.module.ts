import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClientsRoutingModule } from './client-routing.module';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DialogModule } from 'primeng/dialog';
import { TagModule } from 'primeng/tag';
import { TooltipModule } from 'primeng/tooltip';
import { InputTextModule } from 'primeng/inputtext';
import { RippleModule } from 'primeng/ripple';
import { ButtonModule } from 'primeng/button';
import { TableModule } from 'primeng/table';
import { ToastModule } from 'primeng/toast';
import { ClientsListComponent } from './pages/clients-list/clients-list.component';
import { AddClientComponent } from './components/add-client/add-client.component';
import { DialogService } from 'primeng/dynamicdialog';
import { FileUploadModule } from 'primeng/fileupload';
import { SharedModule } from 'src/app/shared/shared.module';
import { MenuModule } from 'primeng/menu';

@NgModule({
  imports: [
    ClientsRoutingModule,
    CommonModule,
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
    ReactiveFormsModule,
    FileUploadModule,
    SharedModule,
    MenuModule,
  ],
  declarations: [
    ClientsListComponent,
    AddClientComponent
  ],
  providers: [
    DialogService
  ],
})
export class ClientsModule { }
