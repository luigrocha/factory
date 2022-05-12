import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ColorA } from 'src/app/types/colorA.types';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ColorAService {
  httpOptions = { headers: new HttpHeaders({ 'Content-type': 'application/json' }) };

  URL_USER = environment.appApiUrl + '/colors-a';

  constructor(private http: HttpClient) { }

  getAll(): Observable<ColorA[]> {
    return this.http.get<ColorA[]>(this.URL_USER + '', this.httpOptions);
  }

  create(user: ColorA): Observable<any> {
    return this.http.post<any>(this.URL_USER + '/', user, this.httpOptions);
  }

  update(id: string, user: ColorA): Observable<any> {
    return this.http.put<any>(this.URL_USER + '/' + id, user, this.httpOptions);
  }

  delete(id: string) {
    return this.http.delete<any>(this.URL_USER + '/' + id, this.httpOptions);
  }

}
