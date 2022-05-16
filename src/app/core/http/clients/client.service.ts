import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Client } from 'src/app/types/client.types';

@Injectable({
  providedIn: 'root'
})
export class ClientService {

  private readonly URL = environment.appApiUrl + '/clients';

  constructor(private http: HttpClient) { }

  getAllClients(): Observable<Client[]> {
    return this.http.get<Client[]>(this.URL); 
  }

}
