import {Component} from '@angular/core';
import {MitarbeiterRestService} from "../../services/mitarbeiter-rest.service";
import {Observable} from "rxjs";
import {Mitarbeiter} from "../../types/mitarbeiter.type";

@Component({
    selector: 'mitarbeiter-dashboard',
    templateUrl: './mitarbeiter-dashboard.component.html',
    styleUrl: './mitarbeiter-dashboard.component.scss'
})
export class MitarbeiterDashboardComponent {

    mitarbeiter$: Observable<Mitarbeiter[]>

    constructor(mitarbeiterRestService: MitarbeiterRestService) {
        this.mitarbeiter$ = mitarbeiterRestService.findAll();
    }

}
