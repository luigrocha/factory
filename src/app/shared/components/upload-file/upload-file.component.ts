import { Component, OnInit } from '@angular/core';
import { INVALID_FILE_SIZE_DETAIL_TEMPLATE, INVALID_FILE_SIZE_SUMMARY_TEMPLATE } from 'src/app/core/constants/upload';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';

@Component({
  selector: 'app-upload-file',
  templateUrl: './upload-file.component.html',
  styleUrls: ['./upload-file.component.scss']
})
export class UploadFileComponent implements OnInit {

  isMultiple: boolean = false;
  accept: string = 'image/*';
  maxSizeSummary = INVALID_FILE_SIZE_SUMMARY_TEMPLATE;
  maxSizeDetail = INVALID_FILE_SIZE_DETAIL_TEMPLATE;
  maxFileSize: number = 3000000;

  constructor(
    public ref: DynamicDialogRef,
    public config: DynamicDialogConfig
  ) { }

  ngOnInit(): void {
    this.accept = this.config.data.accept;
  }

  selectFile($event: any) {
    if (!this.isMultiple) {
      return this.ref.close($event.files[0]);
    }

    return this.ref.close($event.files);
  }
}
