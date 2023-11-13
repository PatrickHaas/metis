import {Component, Input} from '@angular/core';
import {SideBarElement} from "./side-bar-element.type";
import {AuthService} from "../../services/auth.service";

@Component({
  selector: 'side-bar',
  templateUrl: './side-bar.component.html',
  styleUrls: ['./side-bar.component.scss']
})
export class SideBarComponent {

  @Input()
  elements: SideBarElement[] = [];

  constructor(private authService: AuthService) {
  }

  get allowedElements() {
    return this.elements.filter(element => !element.requiredPermissions || this.authService.hasPermission(element.requiredPermissions))
  }
}
