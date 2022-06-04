import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/core/auth/service/auth.service';
import { Preferences } from 'src/app/types/preferences.types';
import { LayoutComponent } from '../../layout.component';
import { PreferencesService } from 'src/app/core/http/preferences/preferences.service';
import { LayoutService } from 'src/app/core/services/layout.service';
import { Config } from 'src/app/types/config.types';

@Component({
  selector: 'app-config',
  templateUrl: 'config.component.html'
})
export class ConfigComponent implements OnInit {
  themes: any[];

  theme: string;

  preferences: Preferences;

  refreshTrafficChart = 'refreshTrafficChart';

  config: Config;

  constructor(
    public appMain: LayoutComponent,
    private authService: AuthService,
    private preferenceService: PreferencesService,
    private layoutService: LayoutService
  ) { }

  get userName(): string {
    return this.authService.getLoggedUser().preferred_username;
  }

  ngOnInit() {
    this.layoutService.config$
      .subscribe(config => {
        this.config = config;
      });
    this.themes = [
      { name: 'denim', color: '#2f8ee5' },
      { name: 'sea-green', color: '#30A059' },
      { name: 'amber', color: '#D49341' },
    ];
    setTimeout(() => {
      this.onLayoutModeChange(null);
    }, 300);
  }

  onLayoutModeChange(event) {
    this.config.menuTheme = this.config.layoutMode;
    this.config.topbarTheme = this.config.layoutMode;
    this.layoutService.setConfig(this.config);
    this.theme = this.config.color;

    const layoutLink: HTMLLinkElement = document.getElementById(
      'layout-css'
    ) as HTMLLinkElement;
    const layoutHref =
      'assets/layout/css/layout-' + this.config.layoutMode + '.css';
    this.replaceLink(layoutLink, layoutHref);

    const themeLink = document.getElementById('theme-css');
    const urlTokens = themeLink.getAttribute('href').split('/');
    urlTokens[urlTokens.length - 1] =
      'theme-' + this.config.layoutMode + '.css';
    urlTokens[2] = this.theme;
    const newURL = urlTokens.join('/');

    this.replaceLink(
      themeLink,
      newURL,
      this.appMain[this.refreshTrafficChart]
    );
  }

  changeTheme(theme) {
    this.theme = theme;
    const themeLink: HTMLLinkElement = document.getElementById(
      'theme-css'
    ) as HTMLLinkElement;
    const themeHref =
      'assets/theme/' + theme + '/theme-' + this.config.layoutMode + '.css';
    this.replaceLink(
      themeLink,
      themeHref,
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

  onConfig() {
    this.appMain.configActive = !this.appMain.configActive;
    this.appMain.configClick = true;
  }

  onConfigButtonClick(event) {
    this.onConfig();
    event.preventDefault();
  }

  onUpdatePreferences() {
    this.updatePreferencesByUsername(this.userName);
    this.onConfig();
  }

  updatePreferencesByUsername(userName: string) {
    const prefernces: Preferences = {
      colorMode: this.config.layoutMode,
      menuMode: this.config.menuMode,
      menuTheme: this.config.menuTheme,
      topBarMode: this.config.topbarTheme,
      color: this.theme
    };
    this.preferenceService
      .updatePreferencesByUsername(userName, prefernces)
      .subscribe(
        (data) => { },
        (err) => {
          console.log(err);
        }
      );
  }
}
