import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CreateHomopolimero, Homopolimero, UpdateHomopolimero } from 'src/app/types/homopolimero.types';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class HomopolimerosService {

  private readonly URL = environment.appApiUrl + '/homopolymers';

  constructor(private http: HttpClient) {
  }

  getAll(): Observable<Homopolimero[]> {
    return this.http.get<Homopolimero[]>(this.URL);
  }

  create(homopolimero: CreateHomopolimero): Observable<Homopolimero> {
    return this.http.post<Homopolimero>(this.URL, homopolimero);
  }

  update(id: number, homopolimero: UpdateHomopolimero): Observable<Homopolimero> {
    const url = `${this.URL}/${id}`;
    return this.http.patch<Homopolimero>(url, homopolimero);
  }

  delete(id: number): Observable<boolean> {
    const url = `${this.URL}/${id}`;
    return this.http.delete<boolean>(url);
  }
}
