import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ConfigComponent } from './config.component';
import { RadioButtonModule } from 'primeng/radiobutton';
import { FormsModule } from '@angular/forms';
import { InputSwitchModule } from 'primeng/inputswitch';
import { ButtonModule } from 'primeng/button';
import { RippleModule } from 'primeng/ripple';
import { ToastModule } from 'primeng/toast';



@NgModule({
  declarations: [
    ConfigComponent
  ],
  exports: [
    ConfigComponent
  ],
  imports: [
    CommonModule,
    RadioButtonModule,
    FormsModule,
    InputSwitchModule,
    ButtonModule,
    RippleModule,
    ToastModule
  ]
})
export class ConfigModule { }
