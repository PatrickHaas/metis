import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {AppComponent} from './app.component';
import {RouterModule} from "@angular/router";
import {EventTypes, PublicEventsService} from "angular-auth-oidc-client";
import {filter} from "rxjs";
import {CoreModule} from "./core/core.module";
import {MitarbeiterModule} from "./contexts/mitarbeiter/mitarbeiter.module";

@NgModule({
    declarations: [
        AppComponent
    ],
    imports: [
        BrowserModule,
        CoreModule,
        RouterModule.forRoot([]),
        MitarbeiterModule
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
