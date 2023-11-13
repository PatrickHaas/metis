import {Injectable} from '@angular/core';
import {OidcSecurityService, PublicEventsService} from "angular-auth-oidc-client";
import {coerceArray} from "@angular/cdk/coercion";
import {filter, map, Observable} from "rxjs";

export type RequiresPermissionsCondition = 'allOf' | 'oneOf' | 'noneOf';

export type RequiredPermissions = string | string[];

@Injectable({
    providedIn: 'root',
})
export class AuthService {

    private userData: any;
    private authorizationToken: string | null = null;

    constructor(private oidcSecurityService: OidcSecurityService, private readonly eventService: PublicEventsService) {
        this.oidcSecurityService
            .checkAuth()
            .subscribe(({isAuthenticated, userData, accessToken}) => {
                if (!isAuthenticated) {
                    this.login();
                }
            });
        this.oidcSecurityService.userData$.subscribe({
            next: userData => this.userData = userData.userData
        })
        this.oidcSecurityService.getAccessToken().subscribe({
            next: accessToken => this.authorizationToken = accessToken
        })
    }

    get events() {
        return this.eventService.registerForEvents()
    }

    get username$(): Observable<string | null> {
        return this.oidcSecurityService.userData$.pipe(
            map(result => result.userData),
            filter(userData => userData != undefined),
            map(userData => userData['given_name'] as string));
    }

    login() {
        this.oidcSecurityService.authorize();
    }

    logout() {
        this.oidcSecurityService.logoff().subscribe({});
    }

    hasPermission(
        permissions: RequiredPermissions,
        condition: RequiresPermissionsCondition = 'oneOf'
    ): boolean {
        const coercedPermissions = coerceArray(permissions);
        return (
            coercedPermissions &&
            ((condition === 'allOf' &&
                    coercedPermissions.every((r: string) => this.hasResourceOrRealmRole(r))) ||
                (condition === 'oneOf' &&
                    coercedPermissions.some((r: string) => this.hasResourceOrRealmRole(r))) ||
                (condition === 'noneOf' &&
                    coercedPermissions.every((r: string) => !this.hasResourceOrRealmRole(r))))
        );
    }

    private hasResourceOrRealmRole(role: string): boolean {
        return this.userData['roles'].find((r: string) => role == r) !== undefined;
    }

    get roles$(): Observable<string[]> {
        return this.oidcSecurityService.userData$.pipe(
            map(result => result.userData),
            filter(userData => userData != undefined),
            map(userData => userData['roles'] as string[]));
    }

    getAuthorizationToken() {
        return this.authorizationToken;
    }

}
