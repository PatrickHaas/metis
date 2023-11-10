import {DEFAULT_CURRENCY_CODE, LOCALE_ID, NgModule} from '@angular/core';
import {CommonModule, CurrencyPipe, DatePipe, DecimalPipe, registerLocaleData} from '@angular/common';
import {MainNavigationComponent} from "./components/main-navigation/main-navigation.component";

import localeDe from '@angular/common/locales/de';
import {RouterModule} from "@angular/router";
import {HttpClientModule} from "@angular/common/http";
import {AuthModule, LogLevel} from "angular-auth-oidc-client";
import {UserAvatarComponent} from "./components/user-avatar/user-avatar.component";

registerLocaleData(localeDe, 'de');


@NgModule({
    declarations: [
        MainNavigationComponent,
        UserAvatarComponent,
    ],
    exports: [
        MainNavigationComponent,
    ],
    imports: [
        CommonModule,
        RouterModule,
        HttpClientModule,
        AuthModule.forRoot({
            config: {
                triggerAuthorizationResultEvent: true,
                postLoginRoute: '/home',
                forbiddenRoute: '/forbidden',
                unauthorizedRoute: '/unauthorized',
                logLevel: LogLevel.Debug,
                historyCleanupOff: true,
                authority: 'http://localhost:8080/realms/metis',
                redirectUrl: window.location.origin,
                postLogoutRedirectUri: window.location.origin,
                clientId: 'metis-spa',
                scope: 'openid profile email offline_access',
                responseType: 'code',
                silentRenew: true,
                useRefreshToken: true,
            }
        }),
    ],
    providers: [
        {provide: LOCALE_ID, useValue: 'de'},
        {provide: DEFAULT_CURRENCY_CODE, useValue: 'EUR'},
        {provide: CurrencyPipe, useClass: CurrencyPipe},
        {provide: DatePipe, useClass: DatePipe},
        {provide: DecimalPipe, useClass: DecimalPipe}
    ]
})
export class CoreModule {
}
