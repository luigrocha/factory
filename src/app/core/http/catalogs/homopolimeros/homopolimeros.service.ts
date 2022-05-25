import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from 'src/app/core/auth/service/auth.service';
import { Homopolimero } from 'src/app/types/homopolimero.types';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class HomopolimerosService {
  httpOptions = {
    headers: new HttpHeaders(
      {
        'Content-type': 'application/json',
        userName: this.authService.getLoggedUser().preferred_username
      })
  };

  URL_USER = environment.appApiUrl + '/homopolymers';

  constructor(private http: HttpClient, private authService: AuthService) { }

  getAll(): Observable<Homopolimero[]> {
    return this.http.get<Homopolimero[]>(this.URL_USER + '', this.httpOptions);
  }

  create(homopolimero: Homopolimero): Observable<any> {
    return this.http.post<any>(this.URL_USER + '', homopolimero, this.httpOptions);
  }

  update(id: number, homopolimero: Homopolimero): Observable<any> {
    return this.http.patch<any>(this.URL_USER + '/' + id, homopolimero, this.httpOptions);
  }

  delete(id: number) {
    return this.http.delete<any>(this.URL_USER + '/' + id, this.httpOptions);
  }

}
