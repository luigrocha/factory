import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ColorA } from 'src/app/types/colorA.types';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ColorAService {

  private readonly URL = environment.appApiUrl + '/colors-a';

  constructor(private http: HttpClient) {
  }

  getAll(): Observable<ColorA[]> {
    return this.http.get<ColorA[]>(this.URL);
  }

  create(color: ColorA): Observable<ColorA> {
    return this.http.post<ColorA>(this.URL, color);
  }

  update(id: string, color: ColorA): Observable<ColorA> {
    const url = `${this.URL}/${id}`;
    return this.http.patch<ColorA>(url, color);
  }

  delete(id: string): Observable<boolean> {
    const url = `${this.URL}/${id}`;
    return this.http.delete<boolean>(url);
  }
}
