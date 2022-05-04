import { Injectable } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';
import { KeycloakProfile, KeycloakTokenParsed } from 'keycloak-js';

@Injectable()
export class AuthService {

  constructor(private keycloackService: KeycloakService) { }

  public getLoggedUser(): KeycloakTokenParsed | undefined {
    try {
      const userDetails: KeycloakTokenParsed | undefined = this.keycloackService.getKeycloakInstance().idTokenParsed;
      return userDetails;
    } catch (e) {
      console.error('Exeption', e);
      return undefined;
    }
  }

  public isLoggedIn(): Promise<boolean> {
    return this.keycloackService.isLoggedIn();
  }

  public loadUserProfile(): Promise<KeycloakProfile> {
    return this.keycloackService.loadUserProfile();
  }

  public login(): void {
    this.keycloackService.login();
  }

  public logout(): void {
    this.keycloackService.logout(window.location.origin);
  }

  public redirectToProfile(): void {
    this.keycloackService.getKeycloakInstance().accountManagement();
  }

  public getRoles(): string[] {
    return this.keycloackService.getUserRoles();
  }

  public isAdmin(): boolean {
    return this.getRoles().find(rol => rol.includes('realm-admin')) ? true : false;
  }

}
