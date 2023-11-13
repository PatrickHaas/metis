import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {CoreModule} from "../../core/core.module";
import {RouterModule} from "@angular/router";
import {MitarbeiterComponent} from "./mitarbeiter.component";


@NgModule({
    declarations: [],
    imports: [
        CommonModule,
        CoreModule,
        RouterModule.forChild([
            {
                path: 'mitarbeiter',
                component: MitarbeiterComponent
            }
        ]),
    ]
})
export class MitarbeiterModule {
}
