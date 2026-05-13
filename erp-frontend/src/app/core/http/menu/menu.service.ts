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

  private readonly URL = environment.appApiUrl + '/menus';

  constructor(private http: HttpClient, private authService: AuthService) { }

  getAllItems(): Observable<Menu[]> {
    return this.http.post<Menu[]>(this.URL + '/findAllItems', this.authService.getRoles());
  }

  getAllItemsTree(): Observable<TreeNode[]> {
    return this.http.get<TreeNode[]>(this.URL + '/findAllItemsTree');
  }

  createItem(item: Menu): Observable<any> {
    return this.http.post<any>(this.URL + '/createItem', item);
  }

  updateItem(id: string, item: Menu): Observable<any> {
    return this.http.put<any>(this.URL + '/updateItem/' + id, item);
  }

  deleteItem(id: string) {
    return this.http.delete<any>(this.URL + '/deleteItem/' + id);
  }
}
