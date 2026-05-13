import {Injectable} from '@angular/core';
import {BehaviorSubject} from 'rxjs';
import {Consolidated} from '../../types/material-request.types';

@Injectable({
  providedIn: 'root'
})
export class MaterialRequestService {
  private consolidated$$ = new BehaviorSubject<Consolidated[]>(null);
  consolidated$ = this.consolidated$$.asObservable();

  constructor() { }

  setConsolidated(consolidated: Consolidated[]) {
    this.consolidated$$.next(consolidated);
  }
}
