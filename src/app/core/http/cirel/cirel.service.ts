import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Cirel } from 'src/app/types/cirel.types';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CirelService {
  httpOptions = { headers: new HttpHeaders({ 'Content-type': 'application/json' }) };

  URL_USER = environment.appApiUrl + '/cirel';

  constructor(private http: HttpClient) { }

  getAll(): Observable<Cirel[]> {
    return this.http.get<Cirel[]>(this.URL_USER + '/', this.httpOptions);
  }

  create(user: Cirel): Observable<any> {
    return this.http.post<any>(this.URL_USER + '/', user, this.httpOptions);
  }

  update(id: string, user: Cirel): Observable<any> {
    return this.http.put<any>(this.URL_USER + '/' + id, user, this.httpOptions);
  }

  delete(id: string) {
    return this.http.delete<any>(this.URL_USER + '/' + id, this.httpOptions);
  }

}
