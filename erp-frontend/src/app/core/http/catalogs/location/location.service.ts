import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CreateLocation, Location, UpdateLocation } from 'src/app/types/celler.types';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class LocationService {

  private readonly URL = environment.appApiUrl + '/locations';

  constructor(private http: HttpClient) {
  }

  getAll(): Observable<Location[]> {
    return this.http.get<Location[]>(this.URL);
  }

  create(location: CreateLocation): Observable<Location> {
    return this.http.post<any>(this.URL, location);
  }

  update(id: number, location: UpdateLocation): Observable<Location> {
    const url = `${this.URL}/${id}`;
    return this.http.patch<Location>(url, location);
  }

  delete(id: number): Observable<boolean> {
    const url = `${this.URL}/${id}`;
    return this.http.delete<any>(url);
  }
}
