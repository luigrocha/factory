import { Component, OnInit } from '@angular/core';
import { KeycloakTokenParsed } from 'keycloak-js';
import { AuthService } from 'src/app/auth/service/auth.service';
import { AppComponent } from '../../app.component';
import { HomeComponent } from '../../pages/home/home.component';

@Component({
    selector: 'app-topbar',
    templateUrl: './app.topbar.component.html'
})
export class AppTopBarComponent implements OnInit {

    userData: KeycloakTokenParsed;
    rolShow = '';

    constructor(public appMain: HomeComponent, public app: AppComponent, private authService: AuthService) {
    }
    ngOnInit() {
        this.userData = this.authService.getLoggedUser();
        this.buildShowRol();
    }

    buildShowRol() {
        const roles = this.authService.getRoles();
        if (roles.find(role => role === 'realm-admin')) {
            this.rolShow = 'Administrador';
        } else if (roles.find(role => role === 'realm-supervisor')) {
            this.rolShow = 'Supervisor';
        } else {
            this.rolShow = 'Operador';
        }
    }

    logout() {
        this.authService.logout();
    }

}
