import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from 'src/app/core/auth/service/auth.service';
import { Printer } from 'src/app/types/printer.types';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PrinterService {
  httpOptions = {
    headers: new HttpHeaders(
      {
        'Content-type': 'application/json',
        userName: this.authService.getLoggedUser().preferred_username
      })
  };

  URL_PRINTER = environment.appApiUrl + '/printers';

  constructor(private http: HttpClient, private authService: AuthService) { }

  getAll(): Observable<Printer[]> {
    return this.http.get<Printer[]>(this.URL_PRINTER + '', this.httpOptions);
  }

  create(user: Printer): Observable<any> {
    return this.http.post<any>(this.URL_PRINTER + '', user, this.httpOptions);
  }

  update(id: number, user: Printer): Observable<any> {
    return this.http.patch<any>(this.URL_PRINTER + '/' + id, user, this.httpOptions);
  }

  delete(id: number) {
    return this.http.delete<any>(this.URL_PRINTER + '/' + id, this.httpOptions);
  }
}
