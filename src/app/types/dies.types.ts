import { Pageable } from 'src/app/types/pageable.types';

export interface DiePageable extends Pageable<Die> {
}

export interface Die {
  id: number;
  code: string;
  name: string;
  createdDate: string;
  description: string;
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
  status: DieStatus;
  machines: string[];
}

export interface DieStatus {
  id: string;
  name: string;
  color: string;
  backgroundColor: string;
}

export interface ShortDie {
  code: string;
  name: string;
}
