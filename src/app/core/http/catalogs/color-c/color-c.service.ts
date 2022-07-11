import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { ColorCatalog, CreateColorCatalog } from 'src/app/types/color-catalog.types';

@Injectable({
  providedIn: 'root'
})
export class ColorCService {

  private readonly URL = environment.appApiUrl + '/color-catalog';

  constructor(private http: HttpClient) {
  }

  getAll(): Observable<ColorCatalog[]> {
    return this.http.get<ColorCatalog[]>(this.URL);
  }

  create(color: CreateColorCatalog): Observable<ColorCatalog> {
    return this.http.post<ColorCatalog>(this.URL, color);
  }

  update(id: number, color: ColorCatalog): Observable<any> {
    const url = `${this.URL}/${id}`;
    return this.http.patch<any>(url, color);
  }

  delete(id: number): Observable<boolean> {
    const url = `${this.URL}/${id}`;
    return this.http.delete<any>(url);
  }
}
