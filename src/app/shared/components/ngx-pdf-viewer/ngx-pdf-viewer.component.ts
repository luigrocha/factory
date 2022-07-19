import { Component, OnInit } from '@angular/core';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { pdfDefaultOptions } from 'ngx-extended-pdf-viewer';

@Component({
  selector: 'app-ngx-pdf-viewer',
  templateUrl: './ngx-pdf-viewer.component.html',
  styleUrls: ['./ngx-pdf-viewer.component.scss']
})
export class NgxPdfViewerComponent implements OnInit {

  fileName: string = 'document.pdf';
  srcPdf: string | ArrayBuffer | Blob | Uint8Array | URL | {
    range: any;
  } = '';

  constructor(
    public ref: DynamicDialogRef,
    public config: DynamicDialogConfig
  ) {
    pdfDefaultOptions.assetsFolder = 'bleeding-edge';
  }

  ngOnInit(): void {
    this.fileName = this.config.data.fileName;
    this.srcPdf = this.config.data.srcPdf;
  }
}
