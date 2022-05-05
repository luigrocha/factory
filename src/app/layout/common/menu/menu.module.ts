import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MenuComponent } from './menu.component';
import { MenuitemComponent } from './menuitem/menuitem.component';
import { RouterModule } from '@angular/router';
import { RippleModule } from 'primeng/ripple';

@NgModule({
  declarations: [
    MenuComponent,
    MenuitemComponent
  ],
  exports: [
    MenuComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    RippleModule
  ]
})
export class MenuModule { }
