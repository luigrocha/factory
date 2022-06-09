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
import { FormsModule } from '@angular/forms';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ThicknessComponent } from './pages/thickness/thickness.component';
import { DropdownModule } from 'primeng/dropdown';
import { AvatarModule } from 'primeng/avatar';
import { ColorCComponent } from './pages/color-c/color-c.component';
import { LocationComponent } from './pages/location/location.component';
import { MenuModule } from 'primeng/menu';

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
  ],
  declarations: [
    ColorAComponent,
    ColorBComponent,
    ColorCComponent,
    HomopolimerosComponent,
    PrintersComponent,
    ThicknessComponent,
    LocationComponent,
  ]
})
export class CatalogsModule { }
