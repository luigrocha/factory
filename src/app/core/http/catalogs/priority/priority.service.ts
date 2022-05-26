import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from 'src/app/core/auth/service/auth.service';
import { Priority } from 'src/app/types/catalogs.types';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PriorityService {
  httpOptions = {
    headers: new HttpHeaders(
      {
        'Content-type': 'application/json',
        userName: this.authService.getLoggedUser().preferred_username
      })
  };

  URL_PRIORITY = environment.appApiUrl + '/priority';

  constructor(private http: HttpClient, private authService: AuthService) { }

  getAllByType(type: string): Observable<Priority[]> {
    return this.http.get<Priority[]>(this.URL_PRIORITY + '/' + type, this.httpOptions);
  }

}
