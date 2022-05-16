import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Thickness } from 'src/app/types/thickness.types';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ThicknessService {
  httpOptions = { headers: new HttpHeaders({ 'Content-type': 'application/json' }) };

  URL_USER = environment.appApiUrl + '/thicknesses';

  constructor(private http: HttpClient) { }

  getAll(): Observable<Thickness[]> {
    return this.http.get<Thickness[]>(this.URL_USER + '', this.httpOptions);
  }

  create(user: Thickness): Observable<any> {
    return this.http.post<any>(this.URL_USER + '/', user, this.httpOptions);
  }

  update(id: string, user: Thickness): Observable<any> {
    return this.http.put<any>(this.URL_USER + '/' + id, user, this.httpOptions);
  }

  delete(id: string) {
    return this.http.delete<any>(this.URL_USER + '/' + id, this.httpOptions);
  }

}
