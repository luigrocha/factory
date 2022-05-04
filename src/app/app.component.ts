import { Component, OnInit } from '@angular/core';
import { PrimeNGConfig } from 'primeng/api';
import { AuthService } from './auth/service/auth.service';
import { PreferencesService } from './components/config/preferences.service';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {

    constructor(
        private primengConfig: PrimeNGConfig,
        private authService: AuthService,
        private preferenceService: PreferencesService
    ) { }

    topbarTheme = 'light';

    menuTheme = 'light';

    layoutMode = 'light';

    menuMode = 'static';

    isRTL = false;

    inputStyle = 'outlined';

    ripple: boolean;

    color = 'amber';

    ngOnInit() {
        this.primengConfig.ripple = true;
        this.getPreferencesByUsername();
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
