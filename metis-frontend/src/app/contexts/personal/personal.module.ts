import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {CoreModule} from "../../core/core.module";
import {RouterModule} from "@angular/router";
import {PersonalComponent} from "./personal.component";
import {MitarbeiterDashboardComponent} from "./mitarbeiter/pages/mitarbeiter-dashboard/mitarbeiter-dashboard.component";
import {MitarbeiterCardComponent} from "./mitarbeiter/components/mitarbeiter-card/mitarbeiter-card.component";


@NgModule({
    declarations: [
        MitarbeiterDashboardComponent,
        MitarbeiterCardComponent
    ],
    imports: [
        CommonModule,
        CoreModule,
        RouterModule.forChild([
            {
                path: 'personal',
                component: PersonalComponent,
                children: [
                    {
                        path: 'mitarbeiter',
                        component: MitarbeiterDashboardComponent
                    }
                ]
            }
        ]),
    ]
})
export class PersonalModule {
}
