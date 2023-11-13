import {Component, Input} from '@angular/core';
import {Mitarbeiter} from "../../types/mitarbeiter.type";

@Component({
    selector: 'mitarbeiter-card',
    templateUrl: './mitarbeiter-card.component.html',
    styleUrl: './mitarbeiter-card.component.scss'
})
export class MitarbeiterCardComponent {
    @Input()
    mitarbeiter: Mitarbeiter;
}
