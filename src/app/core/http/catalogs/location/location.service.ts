import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from 'src/app/core/auth/service/auth.service';
import { Location } from 'src/app/types/celler.types';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class LocationService {
  httpOptions = {
    headers: new HttpHeaders(
      {
        'Content-type': 'application/json',
        userName: this.authService.getLoggedUser().preferred_username
      })
  };

  URL_LOCATION = environment.appApiUrl + '/location';

  constructor(private http: HttpClient, private authService: AuthService) { }

  getAll(): Observable<Location[]> {
    return this.http.get<Location[]>(this.URL_LOCATION + '', this.httpOptions);
  }

  create(homopolimero: Location): Observable<any> {
    return this.http.post<any>(this.URL_LOCATION + '', homopolimero, this.httpOptions);
  }

  update(id: number, homopolimero: Location): Observable<any> {
    return this.http.patch<any>(this.URL_LOCATION + '/' + id, homopolimero, this.httpOptions);
  }

  delete(id: number) {
    return this.http.delete<any>(this.URL_LOCATION + '/' + id, this.httpOptions);
  }

}
