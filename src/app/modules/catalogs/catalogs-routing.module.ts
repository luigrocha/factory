import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ColorAComponent } from './pages/color-a/color-a.component';
import { ColorBComponent } from './pages/color-b/color-b.component';
import { ColorCComponent } from './pages/color-c/color-c.component';
import { HomopolimerosComponent } from './pages/homopolimeros/homopolimeros.component';
import { LocationComponent } from './pages/location/location.component';
import { PrintersComponent } from './pages/printers/printers.component';
import { ThicknessComponent } from './pages/thickness/thickness.component';

const routes: Routes = [
  {
    path: 'homopolimeros',
    component: HomopolimerosComponent,
  },
  {
    path: 'colores-a',
    component: ColorAComponent,
  },
  {
    path: 'colores-b',
    component: ColorBComponent,
  },
  {
    path: 'colores-c',
    component: ColorCComponent,
  },
  {
    path: 'impresoras',
    component: PrintersComponent,
  },
  {
    path: 'grosor',
    component: ThicknessComponent,
  },
  {
    path: 'ubicacion',
    component: LocationComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CatalogsRoutingModule { }
