import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {CoreModule} from "../../core/core.module";
import {RouterModule} from "@angular/router";
import {PersonalComponent} from "./personal.component";
import {MitarbeiterDashboardComponent} from "./mitarbeiter/pages/mitarbeiter-dashboard/mitarbeiter-dashboard.component";
import {MitarbeiterCardComponent} from "./mitarbeiter/components/mitarbeiter-card/mitarbeiter-card.component";
import {MitarbeiterDetailsComponent} from "./mitarbeiter/pages/mitarbeiter-details/mitarbeiter-details.component";
import {
    MitarbeiterZugewieseneGruppenComponent
} from "./mitarbeiter/components/mitarbeiter-zugewiesene-gruppen/mitarbeiter-zugewiesene-gruppen.component";


@NgModule({
    declarations: [
        MitarbeiterDashboardComponent,
        MitarbeiterCardComponent,
        MitarbeiterDetailsComponent,
        MitarbeiterZugewieseneGruppenComponent,
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
                    },
                    {
                        path: 'mitarbeiter/:id',
                        component: MitarbeiterDetailsComponent
                    }
                ]
            }
        ]),
    ]
})
export class PersonalModule {
}
