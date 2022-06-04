import { Component, OnInit } from '@angular/core';
import { PrimeNGConfig } from 'primeng/api';
import { AuthService } from './core/auth/service/auth.service';
import { PreferencesService } from './core/http/preferences/preferences.service';
import { TranslateService } from '@ngx-translate/core';
import { LayoutService } from 'src/app/core/services/layout.service';
import { Config } from 'src/app/types/config.types';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {

  constructor(
    private primengConfig: PrimeNGConfig,
    private authService: AuthService,
    private preferenceService: PreferencesService,
    private config: PrimeNGConfig,
    private translateService: TranslateService,
    private layoutService: LayoutService
  ) {
  }

  ngOnInit() {
    this.primengConfig.ripple = true;
    this.setTranslation();
    this.getPreferencesByUsername();
  }

  setTranslation(): void {
    this.translateService.addLangs(['es']);
    this.translateService.setDefaultLang('es');
    this.translateService.use('es');
    this.translateService.get('primeng').subscribe(res => this.config.setTranslation(res));
  }

  getPreferencesByUsername() {
    if (this.authService.isLoggedIn()) {
      const userName = this.authService.getLoggedUser().preferred_username;
      this.preferenceService.getPreferencesByUsername(userName)
        .subscribe((config: Config) => {
            this.layoutService.setConfig(config);
          },
        );
    }
  }
}
