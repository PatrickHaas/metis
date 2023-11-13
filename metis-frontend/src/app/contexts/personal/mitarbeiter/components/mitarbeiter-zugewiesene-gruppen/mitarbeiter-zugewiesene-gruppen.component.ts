import {Component, Input} from '@angular/core';
import {Mitarbeiter} from "../../types/mitarbeiter.type";

@Component({
    selector: 'mitarbeiter-zugewiesene-gruppen',
    templateUrl: './mitarbeiter-zugewiesene-gruppen.component.html',
    styleUrl: './mitarbeiter-zugewiesene-gruppen.component.scss'
})
export class MitarbeiterZugewieseneGruppenComponent {
    @Input()
    mitarbeiter: Mitarbeiter

}
