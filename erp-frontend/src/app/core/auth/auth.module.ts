import { APP_INITIALIZER, NgModule } from '@angular/core';
import { KeycloakAngularModule, KeycloakService } from 'keycloak-angular';
import { AuthGuard } from 'src/app/core/auth/auth.guard';
import { initializer } from 'src/app/core/auth/keycloak-initializer';
import { AuthService } from 'src/app/core/auth/service/auth.service';

@NgModule({
  imports: [KeycloakAngularModule],
  providers: [
    {
      provide: APP_INITIALIZER,
      useFactory: initializer,
      multi: true,
      deps: [KeycloakService]
    },
    AuthGuard,
    AuthService
  ]
})
export class AuthModule { }
