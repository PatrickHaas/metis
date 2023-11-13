import {Component, Input} from '@angular/core';
import {PageHeaderAction} from "./page-header-action.type";
import {AuthService} from "../../services/auth.service";

@Component({
  selector: 'page-header',
  templateUrl: './page-header.component.html',
  styleUrls: ['./page-header.component.scss']
})
export class PageHeaderComponent {

  @Input()
  title: string = 'Page Header';

  @Input()
  actions: PageHeaderAction[] = [];

  constructor(private authService: AuthService) {
  }

  get allowedActions() {
    return this.actions.filter(action => !action.requiredPermissions || this.authService.hasPermission(action.requiredPermissions))
  }

}
