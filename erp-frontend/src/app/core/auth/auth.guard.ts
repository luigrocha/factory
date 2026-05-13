import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { KeycloakAuthGuard, KeycloakService } from 'keycloak-angular';

@Injectable()
export class AuthGuard extends KeycloakAuthGuard {

    constructor(protected router: Router, protected keyloakService: KeycloakService) {
        super(router, keyloakService);
    }

    public async isAccessAllowed(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Promise<boolean | UrlTree> {
        if (!this.authenticated) {
            await this.keyloakService.login({
                redirectUri: window.location.origin + state.url,
            });
        }

        const requiredRoles = route.data.roles;

        if (!(requiredRoles instanceof Array) || requiredRoles.length === 0) {
            return true;
        }

        return requiredRoles.every(role => this.roles.includes(role));
    }

}