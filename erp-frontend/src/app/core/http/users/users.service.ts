import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { GeneratedUsername, GenerateUsername, User } from 'src/app/types/user.types';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UsersService {

  private readonly URL = environment.userApi + '/users';

  constructor(private http: HttpClient) {
  }

  getAllUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.URL);
  }

  getUserById(id: string): Observable<User> {
    const url = `${this.URL}/${id}`;
    return this.http.get<User>(url);
  }

  createUser(user: User): Observable<any> {
    return this.http.post<any>(this.URL, user);
  }

  updateUserById(id: string, user: User): Observable<any> {
    return this.http.put<any>(this.URL + '/' + id, user);
  }

  deleteUserById(id: string) {
    return this.http.delete<any>(this.URL + '/' + id);
  }

  getUserByUserName(userNane: string) {
    return this.http.get<User>(this.URL + '/findUserByUserName/' + userNane);
  }

  existsByEmail(email: string) {
    return this.http.get<boolean>(this.URL + '/exists-by-email/' + email);
  }

  generateUsername(body: GenerateUsername) {
    return this.http.post<GeneratedUsername>(this.URL + '/generate-username', body);
  }
}
