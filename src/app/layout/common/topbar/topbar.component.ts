import { Component, OnInit } from '@angular/core';
import { KeycloakTokenParsed } from 'keycloak-js';
import { AppComponent } from 'src/app/app.component';
import { AuthService } from 'src/app/core/auth/service/auth.service';
import { LayoutComponent } from 'src/app/layout/layout.component';
import { Status } from 'src/app/types/catalogs.types';
import { Observable } from 'rxjs';
import { PersonService } from 'src/app/core/http/persons/person.service';
import { UserImage } from 'src/app/types/user.types';
import { UserImageService } from 'src/app/core/services/user-image.service';

@Component({
  selector: 'app-topbar',
  templateUrl: './topbar.component.html',
  styleUrls: ['./topbar.component.scss']
})
export class TopbarComponent implements OnInit {

  userData: KeycloakTokenParsed;
  image: UserImage;
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
    private personService: PersonService,
    private imageUserService: UserImageService
  ) {
  }

  ngOnInit() {
    this.userData = this.authService.getLoggedUser();
    this.buildShowRol();
    if (this.authService.isLoggedIn()) {
      this.personService.getUserImage()
        .subscribe(image => {
          if (image) {
            this.image = image;
            this.imageUserService.setUserImage(image.imageUrl);
          }
        });
    }
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
