import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Preferences } from 'src/app/types/preferences.types';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PreferencesService {

  httpOptions = { headers: new HttpHeaders({ 'Content-type': 'application/json' }) };

  URL_PREFERENCES = environment.userApi + '/preference';

  constructor(private http: HttpClient) { }

  getPreferencesByUsername(userName: string): Observable<Preferences> {
    return this.http.get<Preferences>(this.URL_PREFERENCES + '/findPreferencesByUsername/' + userName, this.httpOptions);
  }

  updatePreferencesByUsername(userName: string, prefernces: Preferences): Observable<any> {
    return this.http.patch<any>(this.URL_PREFERENCES + '/updatePreferencesByUsername/' + userName, prefernces, this.httpOptions);
  }


}
