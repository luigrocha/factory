import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from 'src/app/core/auth/service/auth.service';
import { ColorB } from 'src/app/types/colorB.types';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ColorBService {
  httpOptions = {
    headers: new HttpHeaders(
      {
        'Content-type': 'application/json',
        userName: this.authService.getLoggedUser().preferred_username
      })
  };

  URL_COLOR_B = environment.appApiUrl + '/colors-b';

  constructor(private http: HttpClient, private authService: AuthService) { }

  getAll(): Observable<ColorB[]> {
    return this.http.get<ColorB[]>(this.URL_COLOR_B + '', this.httpOptions);
  }

  create(color: ColorB): Observable<any> {
    return this.http.post<any>(this.URL_COLOR_B + '', color, this.httpOptions);
  }

  update(id: string, color: ColorB): Observable<any> {
    return this.http.patch<any>(this.URL_COLOR_B + '/' + id, color, this.httpOptions);
  }

  delete(id: string) {
    return this.http.delete<any>(this.URL_COLOR_B + '/' + id, this.httpOptions);
  }

}
