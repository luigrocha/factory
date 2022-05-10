import { Component, OnInit } from '@angular/core';
import { KeycloakTokenParsed } from 'keycloak-js';
import { AppComponent } from 'src/app/app.component';
import { AuthService } from 'src/app/core/auth/service/auth.service';
import { LayoutComponent } from 'src/app/layout/layout.component';

@Component({
  selector: 'app-topbar',
  templateUrl: './topbar.component.html',
  styleUrls: ['./topbar.component.scss']
})
export class TopbarComponent implements OnInit {

  userData: KeycloakTokenParsed;
  rolShow = '';

  constructor(
    public appMain: LayoutComponent,
    public app: AppComponent,
    private authService: AuthService
  ) {
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
