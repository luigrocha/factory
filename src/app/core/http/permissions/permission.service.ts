import { HttpClient, HttpHeaders } from '@angular/common/http';
import { TypeofExpr } from '@angular/compiler';
import { Injectable, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TreeNode } from 'primeng/api';
import { Observable } from 'rxjs';
import { Menu } from 'src/app/types/menu.types';
import { Permission, TypePermission } from 'src/app/types/permission';
import { environment } from 'src/environments/environment';
import { AuthService } from '../../auth/service/auth.service';

@Injectable({
  providedIn: 'root'
})
export class PermissionService {

  httpOptions = { headers: new HttpHeaders({ 'Content-type': 'application/json' }) };

  URL_PERMISSION = environment.appApiUrl + '/permission';

  permissionsPage: TypePermission[];

  constructor(private http: HttpClient, private authService: AuthService, private router: Router) { }

  findPermissionsByMenuCode(code: string): Observable<Permission[]> {
    return this.http.get<Permission[]>(this.URL_PERMISSION + '/' + code, this.httpOptions);
  }

  updatePermissionByMenuCode(codeMenu: string, codePermission, data: TypePermission[]): Observable<any> {
    return this.http.patch<any>(this.URL_PERMISSION + '/' + codeMenu + '/' + codePermission, data, this.httpOptions);
  }

  findPermissionPage(): Observable<TypePermission[]> {
    const data = {
      url: this.router.url,
      roles: this.authService.getRoles()
    };
    return this.http.post<TypePermission[]>(this.URL_PERMISSION + '/findPermissionsPage', data, this.httpOptions);
  }

}
