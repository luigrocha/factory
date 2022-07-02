import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoadingComponent } from './components/loading/loading.component';
import { StatusHighlightDirective } from './directives/status-highlight/status-highlight.directive';
import { InputUppercaseDirective } from 'src/app/shared/directives/input-uppercase/input-uppercase.directive';
import { UserAvatarComponent } from './components/user-avatar/user-avatar.component';
import { AvatarModule } from 'primeng/avatar';
import { DocumentViewerComponent } from './components/document-viewer/document-viewer.component';
import { NgxExtendedPdfViewerModule } from 'ngx-extended-pdf-viewer';
import { PdfViewerModule } from 'ng2-pdf-viewer';
import { SafePipe } from './pipes/safe.pipe';
import { UploadFileComponent } from './components/upload-file/upload-file.component';
import { FileUploadModule } from 'primeng/fileupload';
import { YesNoPipe } from './pipes/yes-no.pipe';
import { NumericDirective } from './directives/numeric.directive';

@NgModule({
  declarations: [
    LoadingComponent,
    StatusHighlightDirective,
    InputUppercaseDirective,
    UserAvatarComponent,
    DocumentViewerComponent,
    SafePipe,
    UploadFileComponent,
    YesNoPipe,
    NumericDirective
  ],
  exports: [
    LoadingComponent,
    StatusHighlightDirective,
    InputUppercaseDirective,
    UserAvatarComponent,
    SafePipe,
    YesNoPipe,
    NumericDirective
  ],
  imports: [
    CommonModule,
    AvatarModule,
    NgxExtendedPdfViewerModule,
    PdfViewerModule,
    FileUploadModule
  ]
})
export class SharedModule { }
