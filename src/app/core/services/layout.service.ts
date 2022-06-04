import { Injectable } from '@angular/core';
import { Config } from 'src/app/types/config.types';
import { configs, DEFAULT_CONFIG_ID } from 'src/app/core/constants/configs';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LayoutService {

  defaultConfig = DEFAULT_CONFIG_ID;

  configs: Config[] = configs;

  private _configSubject = new BehaviorSubject(this.configs.find(c => c.id === this.defaultConfig));
  config$ = this._configSubject.asObservable();

  constructor() { }

  setConfig(config: Config) {
    if (config) {
      this._configSubject.next(config);
    }
  }
}
