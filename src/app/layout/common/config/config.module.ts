import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ConfigComponent } from './config.component';
import { RadioButtonModule } from 'primeng/radiobutton';
import { FormsModule } from '@angular/forms';
import { InputSwitchModule } from 'primeng/inputswitch';



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
    InputSwitchModule
  ]
})
export class ConfigModule { }
