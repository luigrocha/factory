import { ColorA } from './colorA.types';

export interface ColorB {
  id: string;
  colorA: ColorA;
  index: number;
  dosage: number;
  description: string;
  colorCode: string;
  observation: string;
}

export interface CreateColorB extends Omit<ColorB, 'colorA'> {
  colorAId: string;
}

export interface UpdateColorB extends Omit<ColorB, 'colorA'> {
  colorAId: string;
}
