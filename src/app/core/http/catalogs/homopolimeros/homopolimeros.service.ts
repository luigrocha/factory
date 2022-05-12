import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Homopolimero } from 'src/app/types/homopolimero.types';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class HomopolimerosService {
  httpOptions = { headers: new HttpHeaders({ 'Content-type': 'application/json' }) };

  URL_USER = environment.appApiUrl + '/homo';

  constructor(private http: HttpClient) { }

  getAll(): Observable<Homopolimero[]> {
    return this.http.get<Homopolimero[]>(this.URL_USER + '/', this.httpOptions);
  }

  create(user: Homopolimero): Observable<any> {
    return this.http.post<any>(this.URL_USER + '/', user, this.httpOptions);
  }

  update(id: string, user: Homopolimero): Observable<any> {
    return this.http.put<any>(this.URL_USER + '/' + id, user, this.httpOptions);
  }

  delete(id: string) {
    return this.http.delete<any>(this.URL_USER + '/' + id, this.httpOptions);
  }

}
