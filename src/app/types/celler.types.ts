import { Material } from './material.types';

export interface Celler {
    id?: number;
    lote?: string;
    amount?: number;
    balance?: number;
    coat?: number;
    pallets?: number;
    weight?: number;
    date?: Date;
    observation?: string;
    material?: Material;
    location?: Location;
    document?: Document;
}

export interface Location {
    id?: number;
    location?: string;
    description?: string;
}

export interface Document {
    id?: number;
    name?: string;
    description?: string;
}