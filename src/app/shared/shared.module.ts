import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoadingComponent } from './components/loading/loading.component';
import { StatusHighlightDirective } from './directives/status-highlight/status-highlight.directive';



@NgModule({
  declarations: [
    LoadingComponent,
    StatusHighlightDirective
  ],
  exports: [
    LoadingComponent,
    StatusHighlightDirective
  ],
  imports: [
    CommonModule
  ]
})
export class SharedModule { }
