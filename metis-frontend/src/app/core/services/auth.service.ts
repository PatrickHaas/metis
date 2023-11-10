import {Injectable} from '@angular/core';
import {BehaviorSubject} from 'rxjs';
import {OidcSecurityService} from "angular-auth-oidc-client";

export type RequiresPermissionsCondition = 'allOf' | 'oneOf' | 'noneOf';

export type RequiredPermissions = string | string[];

@Injectable({
    providedIn: 'root',
})
export class AuthService {
    private isAuthenticatedSubject$ = new BehaviorSubject<boolean>(false);
    public isAuthenticated$ = this.isAuthenticatedSubject$.asObservable();

    private isDoneLoadingSubject$ = new BehaviorSubject<boolean>(false);
    public isDoneLoading$ = this.isDoneLoadingSubject$.asObservable();

    private userData: any;

    constructor(private oidcSecurityService: OidcSecurityService) {
        this.oidcSecurityService
            .checkAuth()
            .subscribe(({isAuthenticated, userData, accessToken}) => {
                console.log('app authenticated', isAuthenticated);
                console.log(`Current access token is '${accessToken}'`);
                if (!isAuthenticated) {
                    this.login();
                } else {
                    this.userData = userData;
                    console.warn(this.userData);
                }
            });
    }

    get username() {
        return this.userData ? this.userData['given_name'] : undefined;
    }

    login() {
        console.log('start login');
        this.oidcSecurityService.authorize();
    }

    logout() {
        this.oidcSecurityService.logoff().subscribe({});
    }

}
