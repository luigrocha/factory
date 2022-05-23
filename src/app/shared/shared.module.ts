import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoadingComponent } from './components/loading/loading.component';
import { StatusHighlightDirective } from './directives/status-highlight/status-highlight.directive';
import { InputUppercaseDirective } from 'src/app/shared/directives/input-uppercase/input-uppercase.directive';

@NgModule({
  declarations: [
    LoadingComponent,
    StatusHighlightDirective,
    InputUppercaseDirective
  ],
  exports: [
    LoadingComponent,
    StatusHighlightDirective,
    InputUppercaseDirective
  ],
  imports: [
    CommonModule
  ]
})
export class SharedModule { }
