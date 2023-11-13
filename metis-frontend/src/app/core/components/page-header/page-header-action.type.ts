import {RequiredPermissions} from "../../services/auth.service";

export interface PageHeaderAction {
  title: string;
  routerLink: any[] | string | null | undefined;
  primary?: boolean;
  requiredPermissions?: RequiredPermissions;
}
