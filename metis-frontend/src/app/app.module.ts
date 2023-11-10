import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {AppComponent} from './app.component';
import {RouterModule} from "@angular/router";
import {AuthModule, EventTypes, LogLevel, PublicEventsService} from "angular-auth-oidc-client";
import {filter} from "rxjs";

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
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
    RouterModule.forRoot([]),
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
  constructor(private readonly eventService: PublicEventsService) {
    this.eventService
      .registerForEvents()
      .pipe(
        filter((notification) => notification.type === EventTypes.ConfigLoaded)
      )
      .subscribe((config) => {
        console.log('ConfigLoaded', config);
      });
  }
}
