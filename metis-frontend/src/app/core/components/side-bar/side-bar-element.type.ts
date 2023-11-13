import {RequiredPermissions} from "../../services/auth.service";

export interface SideBarElement {
  title: string;
  routerLink: any[] | string | null | undefined;
  requiredPermissions?: RequiredPermissions;
}
