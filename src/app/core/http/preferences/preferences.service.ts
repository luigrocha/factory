import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Preferences } from 'src/app/types/preferences.types';
import { environment } from 'src/environments/environment';
import { map } from 'rxjs/operators';
import { Config } from 'src/app/types/config.types';
import { defaultConfig } from 'src/app/core/constants/configs';

@Injectable({
  providedIn: 'root'
})
export class PreferencesService {

  httpOptions = { headers: new HttpHeaders({ 'Content-type': 'application/json' }) };

  URL_PREFERENCES = environment.userApi + '/users/{username}/preferences';

  defaultConfig = defaultConfig;

  constructor(private http: HttpClient) { }

  getPreferencesByUsername(username: string): Observable<Config> {
    const url = this.URL_PREFERENCES.replace('{username}', username);
    return this.http.get<Config>(url, this.httpOptions)
      .pipe(
        map((preferences: Preferences) => {
          if (preferences) {
            const config: Config = {
              id: defaultConfig.id,
              color: preferences.color,
              menuMode: preferences.menuMode,
              topbarTheme: preferences.topBarMode,
              layoutMode: preferences.colorMode,
              menuTheme: preferences.menuTheme,
              inputStyle: defaultConfig.inputStyle,
              isRTL: defaultConfig.isRTL,
              ripple: defaultConfig.ripple
            };
            return config;
          }
          return defaultConfig;
        })
      );
  }

  updatePreferencesByUsername(userName: string, preferences: Preferences): Observable<any> {
    const url = this.URL_PREFERENCES.replace('{username}', userName);
    return this.http.patch<any>(url, preferences, this.httpOptions);
  }
}
