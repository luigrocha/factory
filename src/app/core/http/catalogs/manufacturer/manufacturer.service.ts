import { Injectable } from '@angular/core';
import { environment } from "../../../../../environments/environment";
import { HttpClient } from "@angular/common/http";
import { Manufacturer } from "../../../../types/manufacturer.types";
import { Observable } from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ManufacturerService {

  private readonly URL = environment.appApiUrl + '/manufacturers';

  constructor(private http: HttpClient) { }

  getAllManufacturers(): Observable<Manufacturer[]> {
    return this.http.get<Manufacturer[]>(this.URL);
  }
}
