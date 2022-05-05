import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TopbarComponent } from './topbar.component';
import { InputTextModule } from 'primeng/inputtext';
import { BadgeModule } from 'primeng/badge';
import { RouterModule } from '@angular/router';



@NgModule({
  declarations: [
    TopbarComponent
  ],
  imports: [
    CommonModule,
    InputTextModule,
    BadgeModule,
    RouterModule
  ],
  exports: [
    TopbarComponent
  ]
})
export class TopbarModule { }
