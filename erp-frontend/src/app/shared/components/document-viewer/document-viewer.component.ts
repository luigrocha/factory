import { Component, OnInit } from '@angular/core';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { DocumentInfo } from 'src/app/types/document.types';

@Component({
  selector: 'app-document-viewer',
  templateUrl: './document-viewer.component.html',
  styleUrls: ['./document-viewer.component.scss']
})
export class DocumentViewerComponent implements OnInit {

  fileName: string;
  srcPdf: string;
  document: DocumentInfo;

  constructor(
    public ref: DynamicDialogRef,
    public config: DynamicDialogConfig
  ) { }

  ngOnInit(): void {
    this.fileName = this.config.data.fileName;
    this.document = this.config.data.document;
    this.srcPdf = this.document.documentUrl;
  }
}
