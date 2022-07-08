import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CreateThickness, Thickness, UpdateThickness } from 'src/app/types/thickness.types';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ThicknessService {

  private readonly URL = environment.appApiUrl + '/thicknesses';

  constructor(private http: HttpClient) {
  }

  getAll(): Observable<Thickness[]> {
    return this.http.get<Thickness[]>(this.URL);
  }

  create(thickness: CreateThickness): Observable<Thickness> {
    return this.http.post<any>(this.URL, thickness);
  }

  update(id: number, thickness: UpdateThickness): Observable<Thickness> {
    const url = `${this.URL}/${id}`;
    return this.http.put<Thickness>(url, thickness);
  }

  delete(id: number): Observable<boolean> {
    const url = `${this.URL}/${id}`;
    return this.http.delete<boolean>(url);
  }

}
