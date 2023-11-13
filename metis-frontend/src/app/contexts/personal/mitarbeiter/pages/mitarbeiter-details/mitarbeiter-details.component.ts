import {Component} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {Observable} from "rxjs";
import {Mitarbeiter} from "../../types/mitarbeiter.type";
import {MitarbeiterRestService} from "../../services/mitarbeiter-rest.service";

@Component({
    selector: 'mitarbeiter-details',
    templateUrl: './mitarbeiter-details.component.html',
    styleUrl: './mitarbeiter-details.component.scss'
})
export class MitarbeiterDetailsComponent {

    private readonly id: string;
    mitarbeiter$: Observable<Mitarbeiter>

    constructor(activatedRoute: ActivatedRoute, mitarbeiterRestService: MitarbeiterRestService) {
        this.id = activatedRoute.snapshot.params['id'];
        this.mitarbeiter$ = mitarbeiterRestService.findById(this.id);
    }
}
