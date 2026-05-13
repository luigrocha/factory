import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Permission, TypePermission } from 'src/app/types/permission';
import { environment } from 'src/environments/environment';
import { AuthService } from '../../auth/service/auth.service';

@Injectable({
  providedIn: 'root'
})
export class PermissionService {

  private readonly URL = environment.appApiUrl + '/permissions';

  permissionsPage: TypePermission[];

  constructor(
    private http: HttpClient,
    private authService: AuthService,
    private router: Router
  ) {
  }

  findPermissionsByMenuCode(code: string): Observable<Permission[]> {
    return this.http.get<Permission[]>(this.URL + '/' + code);
  }

  updatePermissionByMenuCode(codeMenu: string, codePermission, data: TypePermission[]): Observable<any> {
    return this.http.patch<any>(this.URL + '/' + codeMenu + '/' + codePermission, data);
  }

  findPermissionPage(): Observable<TypePermission[]> {
    const data = {
      url: this.router.url,
      roles: this.authService.getRoles()
    };
    return this.http.post<TypePermission[]>(this.URL + '/findPermissionsPage', data);
  }
}
