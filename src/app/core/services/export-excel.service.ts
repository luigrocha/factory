import { Injectable } from '@angular/core';

import * as FileSaver from 'file-saver';
import * as XLSX from 'xlsx';
import { EXCEL_EXTENSION, EXCEL_TYPE } from 'src/app/core/constants/excel';
import { formatDate } from '@angular/common';
import { dateTimeFormat, localString } from 'src/app/core/constants/date';

@Injectable({
  providedIn: 'root'
})
export class ExportExcelService {

  constructor() { }

  public exportAsExcelFile(json: any[], excelFileName: string): void {
    const worksheet: XLSX.WorkSheet = XLSX.utils.json_to_sheet(json);
    const workbook: XLSX.WorkBook = { Sheets: { 'data': worksheet }, SheetNames: ['data'] };
    const excelBuffer: any = XLSX.write(workbook, { bookType: 'xlsx', type: 'array' });
    ExportExcelService.saveAsExcelFile(excelBuffer, excelFileName);
  }

  private static saveAsExcelFile(buffer: any, fileName: string): void {
    const data: Blob = new Blob([buffer], {
      type: EXCEL_TYPE
    });
    FileSaver.saveAs(
      data,
      fileName + "_export_" + formatDate(new Date(), dateTimeFormat, localString) + EXCEL_EXTENSION
    );
  }
}
