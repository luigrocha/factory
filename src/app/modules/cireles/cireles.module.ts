import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CirelesListComponent } from './pages/cireles-list/cireles-list.component';
import { CirelesRoutingModule } from './cireles-routing.module';
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
import { AvatarModule } from 'primeng/avatar';
import { MenuModule } from 'primeng/menu';
import { DropdownModule } from 'primeng/dropdown';
import { MultiSelectModule } from 'primeng/multiselect';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { SharedModule } from 'src/app/shared/shared.module';
import { DialogService } from 'primeng/dynamicdialog';
import { CreateCirelComponent } from 'src/app/modules/cireles/pages/create-cirel/create-cirel.component';
import { OverlayPanelModule } from 'primeng/overlaypanel';
import { ChipModule } from 'primeng/chip';


@NgModule({
  imports: [
    CirelesRoutingModule,
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
    AvatarModule,
    MenuModule,
    ReactiveFormsModule,
    DropdownModule,
    MultiSelectModule,
    InputTextareaModule,
    SharedModule,
    OverlayPanelModule,
    ChipModule
  ],
  declarations: [
    CirelesListComponent,
    CreateCirelComponent
  ],
  providers: [
    DialogService
  ],
})
export class CirelesModule {
}
