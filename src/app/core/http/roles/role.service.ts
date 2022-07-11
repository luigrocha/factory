import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Role, RoleEnum, RoleType } from 'src/app/types/role.types';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class RoleService {

  private readonly URL = environment.userApi + '/roles';


  constructor(private http: HttpClient) {
  }

  getAllRole(): Observable<Role[]> {
    return this.http.get<Role[]>(this.URL);
  }

  addRolesUser(id: string, data: string[]): Observable<any> {
    return this.http.patch<any>(this.URL + '/addRolesUser/' + id, data);
  }

  removeRolesUser(id: string, data: string[]): Observable<any> {
    return this.http.patch<any>(this.URL + '/removeRolesUser/' + id, data);
  }

  getRoleType(name: string): RoleType {
    switch (name) {
      case RoleEnum.ADMIN:
        return {name: 'Administrador', color: 'warning'};
      case RoleEnum.SUPERVISOR:
        return {name: 'Supervisor', color: 'info'};
      default:
        return {name: 'Operador', color: 'success'};
    }
  }
}

