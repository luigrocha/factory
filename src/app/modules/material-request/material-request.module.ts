import {LOCALE_ID, NgModule} from '@angular/core';
import {CommonModule, registerLocaleData} from '@angular/common';

import { MaterialRequestRoutingModule } from './material-request-routing.module';
import { MaterialRequestListComponent } from './pages/material-request-list/material-request-list.component';
import { MaterialRequestFormComponent } from './pages/material-request-form/material-request-form.component';
import { ToastModule } from 'primeng/toast';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { RippleModule } from 'primeng/ripple';
import { InputTextModule } from 'primeng/inputtext';
import { StepsModule } from 'primeng/steps';
import { MultiSelectModule } from 'primeng/multiselect';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DropdownModule } from 'primeng/dropdown';
import { SharedModule } from 'src/app/shared/shared.module';
import { MenuModule } from 'primeng/menu';
import localeEs from '@angular/common/locales/es';
import {
  MaterialRequestInfoComponent
} from 'src/app/modules/material-request/components/material-request-info/material-request-info.component';
import {
  MaterialRequestDocComponent
} from 'src/app/modules/material-request/components/material-request-doc/material-request-doc.component';
import { DividerModule } from 'primeng/divider';
import { AutoCompleteModule } from 'primeng/autocomplete';
import { TabViewModule } from 'primeng/tabview';
import { DialogService } from 'primeng/dynamicdialog';

registerLocaleData(localeEs, 'es');

@NgModule({
  declarations: [
    MaterialRequestListComponent,
    MaterialRequestFormComponent,
    MaterialRequestInfoComponent,
    MaterialRequestDocComponent
  ],
  imports: [
    CommonModule,
    MaterialRequestRoutingModule,
    ToastModule,
    TableModule,
    ButtonModule,
    RippleModule,
    InputTextModule,
    StepsModule,
    DividerModule,
    FormsModule,
    ReactiveFormsModule,
    TabViewModule,
    AutoCompleteModule,
    DropdownModule,
    MultiSelectModule,
    FormsModule,
    DropdownModule,
    SharedModule,
    MenuModule,
    ReactiveFormsModule
  ],
  providers: [
    DialogService,
    {provide: LOCALE_ID, useValue: 'es'}
  ]
})
export class MaterialRequestModule {
}
