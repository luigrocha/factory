import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Role, RoleType } from 'src/app/types/role.types';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class RoleService {

  httpOptions = { headers: new HttpHeaders({ 'Content-type': 'application/json' }) };

  URL_ROL = environment.userApi + '/role';

  constructor(private http: HttpClient) { }

  getAllRole(): Observable<Role[]> {
    return this.http.get<Role[]>(this.URL_ROL + '/findAllRole', this.httpOptions);
  }

  addRolesUser(id: string, data: string[]): Observable<any> {
    return this.http.patch<any>(this.URL_ROL + '/addRolesUser/' + id, data, this.httpOptions);
  }

  removeRolesUser(id: string, data: string[]): Observable<any> {
    return this.http.patch<any>(this.URL_ROL + '/removeRolesUser/' + id, data, this.httpOptions);
  }

  getRoleType(name: string): RoleType {
    switch (name) {
      case 'realm-admin':
        return { name: 'Administrador', color: 'warning' };
      case 'realm-supervisor':
        return { name: 'Supervisor', color: 'info' };
      default:
        return { name: 'Operador', color: 'success' };
    }
  }

}

