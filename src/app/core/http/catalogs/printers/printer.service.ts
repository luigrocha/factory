import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Printer } from 'src/app/types/printer.types';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PrinterService {
  httpOptions = { headers: new HttpHeaders({ 'Content-type': 'application/json' }) };

  URL_USER = environment.appApiUrl + '/printer';

  constructor(private http: HttpClient) { }

  getAll(): Observable<Printer[]> {
    return this.http.get<Printer[]>(this.URL_USER + '/', this.httpOptions);
  }

  create(user: Printer): Observable<any> {
    return this.http.post<any>(this.URL_USER + '/', user, this.httpOptions);
  }

  update(id: string, user: Printer): Observable<any> {
    return this.http.put<any>(this.URL_USER + '/' + id, user, this.httpOptions);
  }

  delete(id: string) {
    return this.http.delete<any>(this.URL_USER + '/' + id, this.httpOptions);
  }
}
