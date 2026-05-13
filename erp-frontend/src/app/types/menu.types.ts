export interface Menu {
    id?: string;
    label?: string;
    icon?: string;
    routerLink?: Array<string>;
    items?: Array<Menu>;
    url?: string;
    data?: string;
    order?: number;
    role?: string;
    child?: number;
}
