import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { API_V1_VERSION } from 'src/app/core/constants/api-routes';
import { HttpClient } from '@angular/common/http';
import { AuthService } from 'src/app/core/auth/service/auth.service';
import { Observable } from 'rxjs';
import { Profile } from 'src/app/types/profile.types';
import { UserImage } from 'src/app/types/user.types';

@Injectable({
  providedIn: 'root'
})
export class PersonService {
  private readonly URL = environment.userApi + API_V1_VERSION + '/persons';

  constructor(
    private http: HttpClient,
    private authService: AuthService
  ) { }

  getMyProfile(): Observable<Profile> {
    const url = this.URL + '/profile/' + this.authService.getLoggedUser().preferred_username;
    return this.http.get<Profile>(url);
  }

  getUserImage(username: string): Observable<UserImage> {
    const url = this.URL + '/image/' + this.authService.getLoggedUser().preferred_username;
    return this.http.get<UserImage>(url);
  }
}
