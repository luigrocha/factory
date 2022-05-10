import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { TreeNode } from 'primeng/api';
import { Observable } from 'rxjs';
import { Menu } from 'src/app/types/menu.types';
import { environment } from 'src/environments/environment';
import { AuthService } from '../../auth/service/auth.service';

@Injectable({
  providedIn: 'root'
})
export class MenuService {

  httpOptions = { headers: new HttpHeaders({ 'Content-type': 'application/json' }) };

  URL_MENU = environment.appApiUrl + '/menu';

  constructor(private http: HttpClient, private authService: AuthService) { }

  getAllItems(): Observable<Menu[]> {
    return this.http.post<Menu[]>(this.URL_MENU + '/findAllItems', this.authService.getRoles(), this.httpOptions);
  }

  getAllItemsTree(): Observable<TreeNode[]> {
    return this.http.get<TreeNode[]>(this.URL_MENU + '/findAllItemsTree', this.httpOptions);
  }

  createItem(item: Menu): Observable<any> {
    return this.http.post<any>(this.URL_MENU + '/createItem', item, this.httpOptions);
  }

  updateItem(id: string, item: Menu): Observable<any> {
    return this.http.put<any>(this.URL_MENU + '/updateItem/' + id, item, this.httpOptions);
  }

  deleteItem(id: string) {
    return this.http.delete<any>(this.URL_MENU + '/deleteItem/' + id, this.httpOptions);
  }

}
