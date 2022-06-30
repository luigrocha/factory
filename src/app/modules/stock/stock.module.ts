import { LOCALE_ID, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { StockComponent } from './stock.component';
import { StockRoutingModule } from './stock-routing.module';
import { ToastModule } from 'primeng/toast';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import localeEs from '@angular/common/locales/es';
import { registerLocaleData } from '@angular/common';
import { DropdownModule } from 'primeng/dropdown';
import { ButtonModule } from 'primeng/button';
import { ChartModule } from 'primeng/chart';
import { ProgressBarModule } from 'primeng/progressbar';

registerLocaleData(localeEs, 'es');

@NgModule({
  imports: [
    StockRoutingModule,
    ToastModule,
    FormsModule,
    ReactiveFormsModule,
    DropdownModule,
    ButtonModule,
    ChartModule,
    ProgressBarModule,
    CommonModule
  ],
  declarations: [StockComponent],
  providers: [{ provide: LOCALE_ID, useValue: 'es' }]
})
export class StockModule { }
