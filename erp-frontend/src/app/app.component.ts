import { Component, OnInit } from '@angular/core';
import { PrimeNGConfig } from 'primeng/api';
import { AuthService } from './core/auth/service/auth.service';
import { PreferencesService } from './core/http/preferences/preferences.service';
import { TranslateService } from '@ngx-translate/core';
import { LayoutService } from 'src/app/core/services/layout.service';
import { Config } from 'src/app/types/config.types';
import { LayoutComponent } from 'src/app/layout/layout.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  providers: [
    LayoutComponent
  ]
})
export class AppComponent implements OnInit {
  refreshTrafficChart = 'refreshTrafficChart';

  constructor(
    private primengConfig: PrimeNGConfig,
    private authService: AuthService,
    private preferenceService: PreferencesService,
    private config: PrimeNGConfig,
    private translateService: TranslateService,
    private layoutService: LayoutService,
    public appMain: LayoutComponent
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
            this.onLayoutModeChange(config);
          },
        );
    }
  }

  onLayoutModeChange(config: Config) {
    config.menuTheme = config.layoutMode;
    config.topbarTheme = config.layoutMode;
    this.layoutService.setConfig(config);
    const theme = config.color;

    const layoutLink: HTMLLinkElement = document.getElementById(
      'layout-css'
    ) as HTMLLinkElement;
    const layoutHref =
      'assets/layout/css/layout-' + config.layoutMode + '.css';
    this.replaceLink(layoutLink, layoutHref);

    const themeLink = document.getElementById('theme-css');
    const urlTokens = themeLink.getAttribute('href').split('/');
    urlTokens[urlTokens.length - 1] =
      'theme-' + config.layoutMode + '.css';
    urlTokens[2] = theme;
    const newURL = urlTokens.join('/');

    this.replaceLink(
      themeLink,
      newURL,
      this.appMain[this.refreshTrafficChart]
    );
  }

  isIE() {
    return /(MSIE|Trident\/|Edge\/)/i.test(window.navigator.userAgent);
  }

  replaceLink(linkElement, href, callback?) {
    if (this.isIE()) {
      linkElement.setAttribute('href', href);
      if (callback) {
        callback();
      }
    } else {
      const id = linkElement.getAttribute('id');
      const cloneLinkElement = linkElement.cloneNode(true);

      cloneLinkElement.setAttribute('href', href);
      cloneLinkElement.setAttribute('id', id + '-clone');

      linkElement.parentNode.insertBefore(
        cloneLinkElement,
        linkElement.nextSibling
      );

      cloneLinkElement.addEventListener('load', () => {
        linkElement.remove();
        cloneLinkElement.setAttribute('id', id);

        if (callback) {
          callback();
        }
      });
    }
  }
}
