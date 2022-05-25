import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from 'src/app/core/auth/service/auth.service';
import { Thickness } from 'src/app/types/thickness.types';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ThicknessService {
  httpOptions = {
    headers: new HttpHeaders(
      {
        'Content-type': 'application/json',
        userName: this.authService.getLoggedUser().preferred_username
      })
  };

  URL_THINCKNESS = environment.appApiUrl + '/thicknesses';

  constructor(private http: HttpClient, private authService: AuthService) { }

  getAll(): Observable<Thickness[]> {
    return this.http.get<Thickness[]>(this.URL_THINCKNESS + '', this.httpOptions);
  }

  create(thickness: Thickness): Observable<any> {
    return this.http.post<any>(this.URL_THINCKNESS + '', thickness, this.httpOptions);
  }

  update(id: number, thickness: Thickness): Observable<any> {
    return this.http.patch<any>(this.URL_THINCKNESS + '/' + id, thickness, this.httpOptions);
  }

  delete(id: number) {
    return this.http.delete<any>(this.URL_THINCKNESS + '/' + id, this.httpOptions);
  }

}
