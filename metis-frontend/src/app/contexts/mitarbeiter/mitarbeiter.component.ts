import {Component} from '@angular/core';
import {CommonModule} from '@angular/common';
import {CoreModule} from "../../core/core.module";
import {SideBarElement} from "../../core/components/side-bar/side-bar-element.type";

@Component({
    selector: 'mitarbeiter',
    standalone: true,
    imports: [CommonModule, CoreModule],
    templateUrl: './mitarbeiter.component.html',
    styleUrl: './mitarbeiter.component.scss'
})
export class MitarbeiterComponent {
    sideBarElements: SideBarElement[] = [
        {
            title: 'Alle Mitarbeiter',
            routerLink: ['alle']
        }
    ];
}
