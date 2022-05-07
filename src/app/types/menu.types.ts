export interface Menu {
    id?: string;
    label?: string;
    icon?: string;
    condition?: boolean;
    routerLink?: Array<string>;
    items?: Array<Menu>;
}
