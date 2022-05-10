import { Pageable } from 'src/app/types/pageable.types';

export interface DiePageable extends Pageable<Die> {
}

export interface Die {
  id: number;
  code: string;
  name: string;
  createdDate: string;
  description: string;
  status: string;
  area: number;
  length: number;
  width: number;
  gsmdis: number;
  dsbMultiple: number;
  observations: string;
  quantity: number;
  quantityLength: number;
  separationLength: number;
  quantityWidth: number;
  separationWidth: number;
  borderLength: number;
  borderWidth: number;
  leafLength: number;
  leafWidth: number;
  manufacturer: string;
  machines: string[];
}

export enum DieStatus {
  VI = 'VIGENTE',
  OB = 'OBSOLETO',
  PE = 'PENDIENTE',
  IN = 'INDEFINIDO'
}
