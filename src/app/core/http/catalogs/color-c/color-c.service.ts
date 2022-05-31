import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from 'src/app/core/auth/service/auth.service';
import { ColorA } from 'src/app/types/colorA.types';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ColorCService {
  httpOptions = {
    headers: new HttpHeaders(
      {
        'Content-type': 'application/json',
        userName: this.authService.getLoggedUser().preferred_username
      })
  };

  URL_COLOR_C = environment.appApiUrl + '/colorCatalog';

  constructor(private http: HttpClient, private authService: AuthService) { }

  getAll(): Observable<ColorA[]> {
    return this.http.get<ColorA[]>(this.URL_COLOR_C + '', this.httpOptions);
  }

  create(color: ColorA): Observable<any> {
    return this.http.post<any>(this.URL_COLOR_C + '', color, this.httpOptions);
  }

  update(id: string, color: ColorA): Observable<any> {
    return this.http.patch<any>(this.URL_COLOR_C + '/' + id, color, this.httpOptions);
  }

  delete(id: string) {
    return this.http.delete<any>(this.URL_COLOR_C + '/' + id, this.httpOptions);
  }

}
