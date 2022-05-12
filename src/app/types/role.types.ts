export interface Role {
    id?: string;
    name?: string;
    description?: string;
}

export interface RoleType {
    name: string;
    color: string;
}

export enum RoleEnum {
    ADMIN = 'realm-admin',
    SUPERVISOR = 'realm-supervisor',
    USER = 'realm-user'
}