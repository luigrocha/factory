import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/auth/service/auth.service';
import { Preferences } from 'src/app/types/preferences.types';
import { AppComponent } from '../../app.component';
import { HomeComponent } from '../../pages/home/home.component';
import { PreferencesService } from './preferences.service';

@Component({
    selector: 'app-config',
    templateUrl: 'app.config.component.html'
})
export class AppConfigComponent implements OnInit {
    themes: any[];

    theme: string;

    preferences: Preferences;

    refreshTrafficChart = 'refreshTrafficChart';

    constructor(
        public app: AppComponent,
        public appMain: HomeComponent,
        private authService: AuthService,
        private preferenceService: PreferencesService
    ) { }

    get userName(): string {
        return this.authService.getLoggedUser().preferred_username;
    }

    ngOnInit() {
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
        this.app.menuTheme = this.app.layoutMode;
        this.app.topbarTheme = this.app.layoutMode;
        this.theme = this.app.color;

        const layoutLink: HTMLLinkElement = document.getElementById(
            'layout-css'
        ) as HTMLLinkElement;
        const layoutHref =
            'assets/layout/css/layout-' + this.app.layoutMode + '.css';
        this.replaceLink(layoutLink, layoutHref);

        const themeLink = document.getElementById('theme-css');
        const urlTokens = themeLink.getAttribute('href').split('/');
        urlTokens[urlTokens.length - 1] =
            'theme-' + this.app.layoutMode + '.css';
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
            'assets/theme/' + theme + '/theme-' + this.app.layoutMode + '.css';
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
        this.updatePreferencesByUsername(this.userName, this.app);
        this.onConfig();
    }

    updatePreferencesByUsername(userName: string, app: AppComponent) {
        const prefernces: Preferences = {
            colorMode: app.layoutMode,
            menuMode: app.menuMode,
            menuTheme: app.menuTheme,
            topBarMode: app.topbarTheme,
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
