import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TopbarComponent } from './topbar.component';
import { InputTextModule } from 'primeng/inputtext';
import { BadgeModule } from 'primeng/badge';
import { RouterModule } from '@angular/router';
import { SharedModule } from 'src/app/shared/shared.module';


@NgModule({
  declarations: [
    TopbarComponent
  ],
  imports: [
    CommonModule,
    InputTextModule,
    BadgeModule,
    RouterModule,
    SharedModule
  ],
  exports: [
    TopbarComponent
  ]
})
export class TopbarModule { }
