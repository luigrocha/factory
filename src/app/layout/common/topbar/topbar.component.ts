import { Component, OnInit } from '@angular/core';
import { KeycloakTokenParsed } from 'keycloak-js';
import { AppComponent } from 'src/app/app.component';
import { AuthService } from 'src/app/core/auth/service/auth.service';
import { LayoutComponent } from 'src/app/layout/layout.component';
import { Status } from 'src/app/types/catalogs.types';
import { UserImageService } from 'src/app/core/services/user-image.service';

@Component({
  selector: 'app-topbar',
  templateUrl: './topbar.component.html',
  styleUrls: ['./topbar.component.scss']
})
export class TopbarComponent implements OnInit {

  userData: KeycloakTokenParsed;
  imageUrl: string;
  rolShow = '';
  statusPending: Status = {
    id: 'PEP',
    name: 'Pendiente',
    backgroundColor: '#FFD8B2',
    color: '#805B36'
  }

  constructor(
    public appMain: LayoutComponent,
    public app: AppComponent,
    private authService: AuthService,
    private userImageService: UserImageService
  ) {
  }

  ngOnInit() {
    this.userData = this.authService.getLoggedUser();
    this.buildShowRol();
    this.imageUrl = this.userImageService.userImage;
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
