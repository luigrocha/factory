import {LOCALE_ID, NgModule} from '@angular/core';
import {CommonModule, registerLocaleData} from '@angular/common';

import {MixtureListComponent} from './pages/mixture-list/mixture-list.component';
import {MixtureRoutingModule} from './mixture-routing.module';
import {ToastModule} from 'primeng/toast';
import {ButtonModule} from 'primeng/button';
import {DividerModule} from 'primeng/divider';
import {AutoCompleteModule} from 'primeng/autocomplete';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {InputTextModule} from 'primeng/inputtext';
import {CreateMixtureComponent} from './pages/create-mixture/create-mixture.component';
import {SidebarModule} from 'primeng/sidebar';
import {TooltipModule} from 'primeng/tooltip';
import {RippleModule} from 'primeng/ripple';
import {TagModule} from 'primeng/tag';
import {TableModule} from 'primeng/table';
import {DropdownModule} from 'primeng/dropdown';
import localeEs from '@angular/common/locales/es';
import {InputTextareaModule} from 'primeng/inputtextarea';

registerLocaleData(localeEs, 'es');

@NgModule({
  imports: [
    CommonModule,
    MixtureRoutingModule,
    ToastModule,
    ButtonModule,
    DividerModule,
    AutoCompleteModule,
    FormsModule,
    InputTextModule,
    TooltipModule,
    SidebarModule,
    RippleModule,
    ReactiveFormsModule,
    TagModule,
    TableModule,
    DropdownModule,
    InputTextareaModule,
  ],
  declarations: [
    MixtureListComponent,
    CreateMixtureComponent,
  ],
  providers: [{provide: LOCALE_ID, useValue: 'es'}]
})
export class MixtureModule {
}
