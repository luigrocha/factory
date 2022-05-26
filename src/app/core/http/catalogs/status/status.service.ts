import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from 'src/app/core/auth/service/auth.service';
import { Priority, Status } from 'src/app/types/catalogs.types';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class StatusService {
  httpOptions = {
    headers: new HttpHeaders(
      {
        'Content-type': 'application/json',
        userName: this.authService.getLoggedUser().preferred_username
      })
  };

  URL_STATUS = environment.appApiUrl + '/status';

  constructor(private http: HttpClient, private authService: AuthService) { }

  getAllByType(type: string): Observable<Status[]> {
    return this.http.get<Status[]>(this.URL_STATUS + '/' + type, this.httpOptions);
  }

}
