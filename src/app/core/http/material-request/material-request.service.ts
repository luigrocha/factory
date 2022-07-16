import {Injectable} from '@angular/core';
import {environment} from '../../../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Turns} from '../../../types/material-request.types';

@Injectable({
  providedIn: 'root'
})
export class MaterialRequestService {

  URL_TURN = environment.appApiUrl + '/turns';

  constructor(private http: HttpClient) { }

  getAllValidTurns(): Observable<Turns[]>{
    return this.http.get<Turns[]>(this.URL_TURN);
  }
}
