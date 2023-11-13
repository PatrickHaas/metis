import {DEFAULT_CURRENCY_CODE, LOCALE_ID, NgModule} from '@angular/core';
import {CommonModule, CurrencyPipe, DatePipe, DecimalPipe, registerLocaleData} from '@angular/common';
import {MainNavigationComponent} from "./components/main-navigation/main-navigation.component";

import localeDe from '@angular/common/locales/de';
import {RouterModule} from "@angular/router";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {AuthInterceptor, AuthModule, LogLevel} from "angular-auth-oidc-client";
import {UserAvatarComponent} from "./components/user-avatar/user-avatar.component";
import {HasPermissionDirective} from "./directives/has-permission.directive";
import {DebugModeComponent} from "./components/debug-mode/debug-mode.component";
import {SideBarComponent} from "./components/side-bar/side-bar.component";
import {PageHeaderComponent} from "./components/page-header/page-header.component";
import {PageSpinnerComponent} from "./components/page-spinner/page-spinner.component";
import {SectionHeaderComponent} from "./components/section-header/section-header.component";

registerLocaleData(localeDe, 'de');


@NgModule({
    declarations: [
        MainNavigationComponent,
        UserAvatarComponent,
        DebugModeComponent,
        HasPermissionDirective,
        SideBarComponent,
        PageHeaderComponent,
        PageSpinnerComponent,
        SectionHeaderComponent
    ],
    exports: [
        MainNavigationComponent,
        DebugModeComponent,
        SideBarComponent,
        PageHeaderComponent,
        PageSpinnerComponent,
        SectionHeaderComponent
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
                logLevel: LogLevel.Warn,
                historyCleanupOff: true,
                authority: 'http://localhost:8080/realms/metis',
                redirectUrl: window.location.origin,
                postLogoutRedirectUri: window.location.origin,
                clientId: 'metis-spa',
                scope: 'openid profile email offline_access',
                responseType: 'code',
                silentRenew: true,
                useRefreshToken: true,
                secureRoutes: ['http://localhost:8081/'],
            }
        }),
    ],
    providers: [
        {provide: LOCALE_ID, useValue: 'de'},
        {provide: DEFAULT_CURRENCY_CODE, useValue: 'EUR'},
        {provide: CurrencyPipe, useClass: CurrencyPipe},
        {provide: DatePipe, useClass: DatePipe},
        {provide: DecimalPipe, useClass: DecimalPipe},
        {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true},
    ]
})
export class CoreModule {
}
