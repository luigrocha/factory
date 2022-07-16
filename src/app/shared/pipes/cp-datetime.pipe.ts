import { Pipe, PipeTransform } from '@angular/core';
import { DatePipe } from '@angular/common';
import { dateTimeFormat } from 'src/app/core/constants/date';

@Pipe({
  name: 'cpDatetime'
})
export class CpDatetimePipe implements PipeTransform {

  constructor(private datePipe: DatePipe) {
  }

  transform(value: any, args?: any): any {
    return this.datePipe.transform(value, dateTimeFormat);
  }
}
