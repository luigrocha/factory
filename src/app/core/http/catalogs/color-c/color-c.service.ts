import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from 'src/app/core/auth/service/auth.service';
import { environment } from 'src/environments/environment';
import { ColorCatalog } from 'src/app/types/color-catalog.types';

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

  constructor(
    private http: HttpClient,
    private authService: AuthService) {
  }

  getAll(): Observable<ColorCatalog[]> {
    return this.http.get<ColorCatalog[]>(this.URL_COLOR_C + '', this.httpOptions);
  }

  create(color: ColorCatalog): Observable<any> {
    return this.http.post<any>(this.URL_COLOR_C + '', color, this.httpOptions);
  }

  update(id: number, color: ColorCatalog): Observable<any> {
    return this.http.patch<any>(this.URL_COLOR_C + '/' + id, color, this.httpOptions);
  }

  delete(id: number) {
    return this.http.delete<any>(this.URL_COLOR_C + '/' + id, this.httpOptions);
  }

}
