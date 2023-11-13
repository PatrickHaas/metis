import {Component} from '@angular/core';
import {PageHeaderAction} from "../../../../core/components/page-header/page-header-action.type";

@Component({
    selector: 'mitarbeiter',
    templateUrl: './mitarbeiter.component.html',
    styleUrl: './mitarbeiter.component.scss'
})
export class MitarbeiterComponent {
    headerActions: PageHeaderAction[] = [
        {
            title: 'Mitarbeiter einstellen',
            routerLink: ['einstellen'],
            primary: true,
            requiredPermissions: ['personnel:employees:hire']
        }
    ]
}
