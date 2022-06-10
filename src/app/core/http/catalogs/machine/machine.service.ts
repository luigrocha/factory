import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CreateMachine, Machine, UpdateMachine } from 'src/app/types/machine.types';

@Injectable({
  providedIn: 'root'
})
export class MachineService {

  private readonly URL = environment.appApiUrl + '/machines';

  constructor(private http: HttpClient) { }

  getAllMachines(): Observable<Machine[]> {
    return this.http.get<Machine[]>(this.URL);
  }

  createMachine(body: CreateMachine): Observable<Machine> {
    return this.http.post<Machine>(this.URL, body);
  }

  updateMachine(id: number, body: UpdateMachine): Observable<Machine> {
    const url = `${this.URL}/${id}`;
    return this.http.put<Machine>(url, body);
  }

  deleteMachine(id: number): Observable<boolean> {
    const url = `${this.URL}/${id}`;
    return this.http.delete<boolean>(url);
  }
}
