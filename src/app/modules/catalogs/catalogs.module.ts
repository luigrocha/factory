import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ColorAComponent } from './pages/color-a/color-a.component';
import { ColorBComponent } from './pages/color-b/color-b.component';
import { HomopolimerosComponent } from './pages/homopolimeros/homopolimeros.component';
import { PrintersComponent } from './pages/printers/printers.component';
import { CatalogsRoutingModule } from './catalogs-routing.module';
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
import { ThicknessComponent } from './pages/thickness/thickness.component';
import { DropdownModule } from 'primeng/dropdown';
import { AvatarModule } from 'primeng/avatar';
import { ColorCComponent } from './pages/color-c/color-c.component';
import { LocationComponent } from './pages/location/location.component';
import { MachinesComponent } from './pages/machines/machines.component';
import { MenuModule } from 'primeng/menu';
import { CheckboxModule } from 'primeng/checkbox';
import { MachineModalComponent } from './components/machine-modal/machine-modal.component';
import { DialogService } from 'primeng/dynamicdialog';
import { ThicknessModalComponent } from './components/thickness-modal/thickness-modal.component';
import { HomopolimeroModalComponent } from './components/homopolimero-modal/homopolimero-modal.component';
import { ColorAModalComponent } from './components/color-a-modal/color-a-modal.component';
import { ColorBModalComponent } from './components/color-b-modal/color-b-modal.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { PrinterModalComponent } from './components/printer-modal/printer-modal.component';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { ColorCModalComponent } from './components/color-c-modal/color-c-modal.component';
import { LocationModalComponent } from './components/location-modal/location-modal.component';

@NgModule({
  imports: [
    CommonModule,
    CatalogsRoutingModule,
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
    DropdownModule,
    AvatarModule,
    MenuModule,
    CheckboxModule,
    ReactiveFormsModule,
    SharedModule,
    InputTextareaModule,
  ],
  declarations: [
    ColorAComponent,
    ColorBComponent,
    ColorCComponent,
    HomopolimerosComponent,
    PrintersComponent,
    ThicknessComponent,
    LocationComponent,
    MachinesComponent,
    MachineModalComponent,
    ThicknessModalComponent,
    HomopolimeroModalComponent,
    ColorAModalComponent,
    ColorBModalComponent,
    PrinterModalComponent,
    ColorCModalComponent,
    LocationModalComponent,
  ],
  providers: [
    DialogService
  ]
})
export class CatalogsModule {
}
