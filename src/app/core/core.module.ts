import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Error403Component } from 'src/app/core/error/error403/error403.component';
import { Error404Component } from 'src/app/core/error/error404/error404.component';
import { Error500Component } from 'src/app/core/error/error500/error500.component';
import { RouterModule } from '@angular/router';
import { ButtonModule } from 'primeng/button';
import { RippleModule } from 'primeng/ripple';

@NgModule({
  declarations: [
    Error403Component,
    Error404Component,
    Error500Component
  ],
  imports: [
    CommonModule,
    RouterModule,
    ButtonModule,
    RippleModule
  ]
})
export class CoreModule { }
