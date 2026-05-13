import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CreatePrinter, Printer, UpdatePrinter } from 'src/app/types/printer.types';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PrinterService {

  private readonly URL = environment.appApiUrl + '/printers';

  constructor(private http: HttpClient) {
  }

  getAll(): Observable<Printer[]> {
    return this.http.get<Printer[]>(this.URL);
  }

  create(printer: CreatePrinter): Observable<Printer> {
    return this.http.post<Printer>(this.URL, printer);
  }

  update(id: number, printer: UpdatePrinter): Observable<Printer> {
    const url = `${this.URL}/${id}`;
    return this.http.patch<Printer>(url, printer);
  }

  delete(id: number) {
    const url = `${this.URL}/${id}`;
    return this.http.delete<any>(url);
  }
}
