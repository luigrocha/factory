import {Injectable} from '@angular/core';
import {environment} from 'src/environments/environment';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {AuthService} from '../../auth/service/auth.service';
import {MixtureCreate, MixtureShort} from '../../../types/mixture.types';

@Injectable({
  providedIn: 'root'
})
export class MixtureService {

  private readonly URL = environment.appApiUrl + '/mixture';

  constructor(private http: HttpClient) {
  }

  getNumberToCreate(): Observable<number> {
    return this.http.get<number>(this.URL + '/findNumberToCreate');
  }

  create(body: MixtureCreate): Observable<any> {
    return this.http.post<any>(this.URL, body   );
  }

  search(query: string): Observable<MixtureShort[]> {
    const url = this.URL + '/search?query=' + query;
    return this.http.get<MixtureShort[]>(url);
  }

}
