import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UsersListComponent } from './pages/users-list/users-list.component';
import { UsersRoutingModule } from 'src/app/modules/users/users-routing.module';
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
import { MenuModule } from 'primeng/menu';
import { UserModalComponent } from './components/user-modal/user-modal.component';
import { DialogService } from 'primeng/dynamicdialog';
import { AutoCompleteModule } from 'primeng/autocomplete';


@NgModule({
  declarations: [
    UsersListComponent,
    UserModalComponent
  ],
  imports: [
    CommonModule,
    UsersRoutingModule,
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
    MenuModule,
    ReactiveFormsModule,
    AutoCompleteModule,
  ],
  providers: [
    DialogService
  ],
})
export class UsersModule { }
