import { LOCALE_ID, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { StoreComponent } from './pages/store-list/store.component';
import { StoreRoutingModule } from './store-routing.module';
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
import { StoreCreateComponent } from './pages/store-create/store-create.component';

import localeEs from '@angular/common/locales/es';
import { registerLocaleData } from '@angular/common';
import { MenuModule } from 'primeng/menu';
import { StoreCebComponent } from './pages/store-ceb/store-ceb.component';
import { DataViewModule } from 'primeng/dataview';
import { DropdownModule } from 'primeng/dropdown';

registerLocaleData(localeEs, 'es');

@NgModule({
  imports: [
    CommonModule,
    StoreRoutingModule,
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
    DataViewModule,
    DropdownModule,
  ],
  declarations: [
    StoreComponent,
    StoreCreateComponent,
    StoreCebComponent,
  ],
  providers: [{ provide: LOCALE_ID, useValue: 'es' }]
})
export class StoreModule { }
