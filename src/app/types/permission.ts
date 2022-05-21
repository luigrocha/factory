export interface Permission {
    id?: number;
    role?: string;
    typePermission?: Array<TypePermission>;
}

export interface TypePermission {
    id?: number;
    name?: string;
    flag?: boolean;
}
