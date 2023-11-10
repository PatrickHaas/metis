import {Injectable} from '@angular/core';
import {OidcSecurityService} from "angular-auth-oidc-client";

export type RequiresPermissionsCondition = 'allOf' | 'oneOf' | 'noneOf';

export type RequiredPermissions = string | string[];

@Injectable({
    providedIn: 'root',
})
export class AuthService {

    private userData: any;

    constructor(private oidcSecurityService: OidcSecurityService) {
        this.oidcSecurityService
            .checkAuth()
            .subscribe(({isAuthenticated, userData, accessToken}) => {
                if (!isAuthenticated) {
                    this.login();
                } else {
                    this.userData = userData;
                }
            });
    }

    get username() {
        return this.userData ? this.userData['given_name'] : undefined;
    }

    login() {
        this.oidcSecurityService.authorize();
    }

    logout() {
        this.oidcSecurityService.logoff().subscribe({});
    }

}
