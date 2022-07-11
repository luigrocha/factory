import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ColorB, CreateColorB, UpdateColorB } from 'src/app/types/colorB.types';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ColorBService {

  private readonly URL = environment.appApiUrl + '/colors-b';

  constructor(private http: HttpClient) {
  }

  getAll(): Observable<ColorB[]> {
    return this.http.get<ColorB[]>(this.URL);
  }

  create(color: CreateColorB): Observable<ColorB> {
    return this.http.post<ColorB>(this.URL, color);
  }

  update(id: string, color: UpdateColorB): Observable<any> {
    const url = `${this.URL}/${id}`;
    return this.http.patch<any>(url, color);
  }

  delete(id: string): Observable<boolean> {
    const url = `${this.URL}/${id}`;
    return this.http.delete<boolean>(url);
  }
}
