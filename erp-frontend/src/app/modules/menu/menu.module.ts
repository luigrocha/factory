import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MenuComponent } from './menu.component';

import { TreeModule } from 'primeng/tree';
import { TreeTableModule } from 'primeng/treetable';
import { ButtonModule } from 'primeng/button';
import { MenuRoutingModule } from './menu-routing.module';
import { RippleModule } from 'primeng/ripple';
import { MenuModule as MenuModulePrimeng } from 'primeng/menu';
import { ToastModule } from 'primeng/toast';
import { TooltipModule } from 'primeng/tooltip';
import { DialogModule } from 'primeng/dialog';
import { FormsModule } from '@angular/forms';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { DropdownModule } from 'primeng/dropdown';
import { InputTextModule } from 'primeng/inputtext';
import { CheckboxModule } from 'primeng/checkbox';
import { DividerModule } from 'primeng/divider';

@NgModule({
  imports: [
    MenuRoutingModule,
    TreeModule,
    TreeTableModule,
    RippleModule,
    ButtonModule,
    MenuModulePrimeng,
    TooltipModule,
    DialogModule,
    FormsModule,
    ConfirmDialogModule,
    DropdownModule,
    ToastModule,
    InputTextModule,
    CheckboxModule,
    DividerModule,
    CommonModule
  ],
  declarations: [MenuComponent]
})
export class MenuModule { }
