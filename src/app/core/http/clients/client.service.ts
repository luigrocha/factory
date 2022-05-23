import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Client, CreateClient } from 'src/app/types/client.types';

@Injectable({
  providedIn: 'root'
})
export class ClientService {

  private readonly URL = environment.appApiUrl + '/clients';

  constructor(private http: HttpClient) { }

  getAllClients(): Observable<Client[]> {
    return this.http.get<Client[]>(this.URL); 
  }

  createClient(client: CreateClient): Observable<Client> {
    const body: FormData = new FormData();
    body.append('id', client.id);
    body.append('name', client.name);

    if (client.file) {
      body.append('file', client.file);
    }

    return this.http.post<Client>(this.URL, body);
  }

  deleteClient(id: string): Observable<boolean> {
    return this.http.delete<boolean>(`${this.URL}/${id}`);
  }
}
