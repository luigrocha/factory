import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RightpanelComponent } from './rightpanel.component';
import { CheckboxModule } from 'primeng/checkbox';
import { FormsModule } from '@angular/forms';


@NgModule({
  declarations: [
    RightpanelComponent
  ],
  exports: [
    RightpanelComponent
  ],
  imports: [
    CommonModule,
    CheckboxModule,
    FormsModule
  ]
})
export class RightpanelModule { }
