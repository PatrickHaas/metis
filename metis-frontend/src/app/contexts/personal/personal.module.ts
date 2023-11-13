import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {CoreModule} from "../../core/core.module";
import {RouterModule} from "@angular/router";
import {PersonalComponent} from "./personal.component";
import {MitarbeiterComponent} from "./pages/mitarbeiter/mitarbeiter.component";


@NgModule({
    declarations: [
        MitarbeiterComponent
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
                        component: MitarbeiterComponent
                    }
                ]
            }
        ]),
    ]
})
export class PersonalModule {
}
