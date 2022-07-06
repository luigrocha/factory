import {Injectable} from '@angular/core';
import {environment} from 'src/environments/environment';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {AuthService} from '../../auth/service/auth.service';
import {MixtureCreate} from '../../../types/mixture.types';

@Injectable({
  providedIn: 'root'
})
export class MixtureService {
  httpOptions = {
    headers: new HttpHeaders(
      {
        'Content-type': 'application/json',
        userName: this.authService.getLoggedUser().preferred_username
      })
  };

  private readonly URL = environment.appApiUrl + '/mixture';

  constructor(private http: HttpClient, private authService: AuthService) {
  }

  getNumberToCreate(): Observable<number> {
    return this.http.get<number>(this.URL + '/findNumberToCreate', this.httpOptions);
  }

  create(body: MixtureCreate): Observable<any> {
    return this.http.post<any>(this.URL, body, this.httpOptions);
  }

}
