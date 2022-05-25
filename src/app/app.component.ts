import { Component, OnInit } from '@angular/core';
import { PrimeNGConfig } from 'primeng/api';
import { AuthService } from './core/auth/service/auth.service';
import { PreferencesService } from './core/http/preferences/preferences.service';
import { TranslateService } from '@ngx-translate/core';


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
  ) {
  }

  topbarTheme = 'light';

  menuTheme = 'light';

  layoutMode = 'light';

  menuMode = 'static';

  isRTL = false;

  inputStyle = 'outlined';

  ripple: boolean;

  color = 'denim';

  ngOnInit() {
    this.primengConfig.ripple = true;
    this.getPreferencesByUsername();
    this.setTranslation();
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
      this.preferenceService.getPreferencesByUsername(userName).subscribe(
        (data) => {
          this.topbarTheme = data.topBarMode;
          this.menuTheme = data.menuTheme;
          this.layoutMode = data.colorMode;
          this.menuMode = data.menuMode;
          this.color = data.color;
        },
      );
    }
  }

}
