import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ColorB } from 'src/app/types/colorB.types';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ColorBService {
  httpOptions = { headers: new HttpHeaders({ 'Content-type': 'application/json' }) };

  URL_USER = environment.appApiUrl + '/colors-b';

  constructor(private http: HttpClient) { }

  getAll(): Observable<ColorB[]> {
    return this.http.get<ColorB[]>(this.URL_USER + '', this.httpOptions);
  }

  create(user: ColorB): Observable<any> {
    return this.http.post<any>(this.URL_USER + '/', user, this.httpOptions);
  }

  update(id: string, user: ColorB): Observable<any> {
    return this.http.put<any>(this.URL_USER + '/' + id, user, this.httpOptions);
  }

  delete(id: string) {
    return this.http.delete<any>(this.URL_USER + '/' + id, this.httpOptions);
  }

}
