import {Component, Input} from '@angular/core';
import {SectionHeaderAction} from "./section-header-action.type";
import {AuthService} from "../../services/auth.service";

@Component({
  selector: 'section-header',
  templateUrl: './section-header.component.html',
  styleUrls: ['./section-header.component.scss']
})
export class SectionHeaderComponent {

  @Input()
  title: string = 'Section Header';

  @Input()
  actions: SectionHeaderAction[] = [];

  constructor(private authService: AuthService) {
  }

  get allowedActions() {
    return this.actions.filter(action => !action.requiredPermissions || this.authService.hasPermission(action.requiredPermissions))
  }

}
