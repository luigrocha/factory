import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Manufacturer } from 'src/app/types/manufacturer.types';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ManufacturerService {
  httpOptions = {
    headers: new HttpHeaders(
      {
        'Content-type': 'application/json'
      })
  };

  private readonly URL = environment.appApiUrl + '/manufacturers';

  constructor(private http: HttpClient) { }

  getAllManufacturers(): Observable<Manufacturer[]> {
    return this.http.get<Manufacturer[]>(this.URL);
  }

  create(manufacturer: Manufacturer): Observable<Manufacturer> {
    return this.http.post<Manufacturer>(this.URL, manufacturer);
  }

  update(code: number, manufacturer: Manufacturer): Observable<Manufacturer> {
    return this.http.put<Manufacturer>(`${this.URL}/${code}`, manufacturer);
  }

  delete(code: number): Observable<any> {
    return this.http.delete(`${this.URL}/${code}`);
  }
}
