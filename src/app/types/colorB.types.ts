import { ColorA } from './colorA.types';

export interface ColorB {
    id?: string;
    colorA?: ColorA;
    index?: number;
    dosage?: number;
    description?: string;
    colorCode?: string;
}
