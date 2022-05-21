import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
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

  constructor(private http: HttpClient, private authService: AuthService) { }

  findPermissionsByMenuCode(code: string): Observable<Permission[]> {
    return this.http.get<Permission[]>(this.URL_PERMISSION + '/' + code, this.httpOptions);
  }

  updatePermissionByMenuCode(codeMenu: string, codePermission, data: TypePermission[]): Observable<any> {
    return this.http.patch<any>(this.URL_PERMISSION + '/' + codeMenu + '/' + codePermission, data, this.httpOptions);
  }

}
