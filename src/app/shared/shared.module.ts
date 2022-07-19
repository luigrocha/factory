import { NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
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
import { ConfirmationTextComponent } from './components/confirmation-text/confirmation-text.component';
import { ReactiveFormsModule } from '@angular/forms';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { RippleModule } from 'primeng/ripple';
import { CpDatePipe } from './pipes/cp-date.pipe';
import { CpDatetimePipe } from './pipes/cp-datetime.pipe';
import { NgxPdfViewerComponent } from './components/ngx-pdf-viewer/ngx-pdf-viewer.component';

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
    NumericDirective,
    ConfirmationTextComponent,
    CpDatePipe,
    CpDatetimePipe,
    NgxPdfViewerComponent
  ],
  exports: [
    LoadingComponent,
    StatusHighlightDirective,
    InputUppercaseDirective,
    UserAvatarComponent,
    SafePipe,
    YesNoPipe,
    NumericDirective,
    CpDatePipe,
    CpDatetimePipe
  ],
  imports: [
    CommonModule,
    AvatarModule,
    NgxExtendedPdfViewerModule,
    PdfViewerModule,
    FileUploadModule,
    ReactiveFormsModule,
    InputTextareaModule,
    RippleModule
  ],
  providers: [
    DatePipe
  ]
})
export class SharedModule { }
