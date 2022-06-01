import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoadingComponent } from './components/loading/loading.component';
import { StatusHighlightDirective } from './directives/status-highlight/status-highlight.directive';
import { InputUppercaseDirective } from 'src/app/shared/directives/input-uppercase/input-uppercase.directive';
import { UserAvatarComponent } from './components/user-avatar/user-avatar.component';
import { AvatarModule } from 'primeng/avatar';

@NgModule({
  declarations: [
    LoadingComponent,
    StatusHighlightDirective,
    InputUppercaseDirective,
    UserAvatarComponent
  ],
  exports: [
    LoadingComponent,
    StatusHighlightDirective,
    InputUppercaseDirective,
    UserAvatarComponent
  ],
  imports: [
    CommonModule,
    AvatarModule
  ]
})
export class SharedModule { }
