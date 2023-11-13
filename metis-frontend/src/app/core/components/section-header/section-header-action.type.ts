import {RequiredPermissions} from "../../services/auth.service";

export interface SectionHeaderAction {
  title: string;
  routerLink?: any[] | string | null | undefined;
  click?: () => void;
  requiredPermissions?: RequiredPermissions
}
