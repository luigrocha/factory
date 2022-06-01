import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from 'src/app/core/auth/service/auth.service';
import { Celler } from 'src/app/types/celler.types';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CellerService {
  httpOptions = {
    headers: new HttpHeaders(
      {
        'Content-type': 'application/json',
        userName: this.authService.getLoggedUser().preferred_username
      })
  };

  URL_CELLER = environment.appApiUrl + '/celler';

  constructor(private http: HttpClient, private authService: AuthService) { }

  getAll(): Observable<Celler[]> {
    return this.http.get<Celler[]>(this.URL_CELLER + '', this.httpOptions);
  }

  getByMaterialCode(id: number): Observable<Celler[]> {
    return this.http.get<Celler[]>(this.URL_CELLER + '/findByMaterialCode/' + id, this.httpOptions);
  }

}
