import {Component} from '@angular/core';
import {CommonModule} from '@angular/common';
import {CoreModule} from "../../core/core.module";
import {SideBarElement} from "../../core/components/side-bar/side-bar-element.type";

@Component({
    selector: 'personal',
    standalone: true,
    imports: [CommonModule, CoreModule],
    templateUrl: './personal.component.html',
    styleUrl: './personal.component.scss'
})
export class PersonalComponent {
    sideBarElements: SideBarElement[] = [
        {
            title: 'Mitarbeiter',
            routerLink: ['mitarbeiter']
        }
    ];
}
