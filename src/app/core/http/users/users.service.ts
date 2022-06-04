import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';

import { User } from 'src/app/types/user.types';
import { environment } from 'src/environments/environment';


@Injectable({
  providedIn: 'root'
})
export class UsersService {

  httpOptions = {headers: new HttpHeaders({'Content-type': 'application/json'})};

  URL_USER = environment.userApi + '/users';

  constructor(private http: HttpClient) {
  }

  getAllUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.URL_USER, this.httpOptions);
  }

  getUserById(id: string): Observable<User> {
    return this.http.get<User>(this.URL_USER + '/' + id, this.httpOptions);
  }

  createUser(user: User): Observable<any> {
    return this.http.post<any>(this.URL_USER, user, this.httpOptions);
  }

  updateUserById(id: string, user: User): Observable<any> {
    return this.http.put<any>(this.URL_USER + '/' + id, user, this.httpOptions);
  }

  deleteUserById(id: string) {
    return this.http.delete<any>(this.URL_USER + '/' + id, this.httpOptions);
  }

  getUserByUserName(userNane: string) {
    return this.http.get<User>(this.URL_USER + '/findUserByUserName/' + userNane, this.httpOptions);
  }
}
