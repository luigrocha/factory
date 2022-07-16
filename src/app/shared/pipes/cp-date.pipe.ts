import { Pipe, PipeTransform } from '@angular/core';
import { DatePipe } from '@angular/common';
import { dateFormat } from 'src/app/core/constants/date';

@Pipe({
  name: 'cpDate'
})
export class CpDatePipe implements PipeTransform {

  constructor(private datePipe: DatePipe) {}

  transform(value: any, args?: any): any {
    return this.datePipe.transform(value, dateFormat);
  }
}
