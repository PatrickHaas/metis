import {Component} from '@angular/core';
import {AuthService} from '../../services/auth.service';

@Component({
    selector: 'user-avatar',
    templateUrl: './user-avatar.component.html',
    styleUrls: ['./user-avatar.component.scss'],
})
export class UserAvatarComponent {
    constructor(private authService: AuthService) {
    }

    get username$() {
        return this.authService.username$;
    }

    logout() {
        this.authService.logout();
    }
}
