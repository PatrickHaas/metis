import {Directive, EmbeddedViewRef, Input, TemplateRef, ViewContainerRef,} from '@angular/core';
import {coerceArray} from '@angular/cdk/coercion';
import {AuthService, RequiredPermissions, RequiresPermissionsCondition,} from '../services/auth.service';

@Directive({
  selector: '[hasPermission]',
})
export class HasPermissionDirective {
  private condition: RequiresPermissionsCondition = 'oneOf';

  private permissions: RequiredPermissions = [];

  private embeddedViewRef?: EmbeddedViewRef<unknown>;

  @Input()
  set hasPermissionCondition(config: RequiresPermissionsCondition) {
    this.condition = config;
    this.render();
  }

  @Input()
  set hasPermission(permissions: RequiredPermissions) {
    this.permissions = coerceArray(permissions);
    this.render();
  }

  private render(): void {
    if (this.authService.hasPermission(this.permissions, this.condition)) {
      if (!this.embeddedViewRef) {
        this.embeddedViewRef = this.viewContainer.createEmbeddedView(
          this.templateRef
        );
      }
    } else {
      this.viewContainer.clear();
      this.embeddedViewRef = undefined;
    }
  }

  constructor(
    private readonly templateRef: TemplateRef<unknown>,
    private readonly viewContainer: ViewContainerRef,
    private readonly authService: AuthService
  ) {
    this.authService.events.subscribe(() => this.render());
  }
}
