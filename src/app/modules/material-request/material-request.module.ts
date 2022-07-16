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
import { MaterialRequestInfoComponent } from './components/material-request-info/material-request-info.component';
import {DividerModule} from 'primeng/divider';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import localeEs from '@angular/common/locales/es';
import {TabViewModule} from 'primeng/tabview';
import {AutoCompleteModule} from 'primeng/autocomplete';

registerLocaleData(localeEs, 'es');

@NgModule({
  declarations: [
    MaterialRequestListComponent,
    MaterialRequestFormComponent,
    MaterialRequestInfoComponent
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
  ],
  providers: [{provide: LOCALE_ID, useValue: 'es'}]
})
export class MaterialRequestModule { }
