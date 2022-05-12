import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ColorAComponent } from './pages/color-a/color-a.component';
import { ColorBComponent } from './pages/color-b/color-b.component';
import { HomopolimerosComponent } from './pages/homopolimeros/homopolimeros.component';
import { PrintersComponent } from './pages/printers/printers.component';
import { CatalogsRoutingModule } from './catalogs-routing.module';



@NgModule({
  imports: [
    CommonModule,
    CatalogsRoutingModule,
  ],
  declarations: [
    ColorAComponent,
    ColorBComponent,
    HomopolimerosComponent,
    PrintersComponent
  ]
})
export class CatalogsModule { }
