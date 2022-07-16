import { Pageable } from 'src/app/types/pageable.types';
import { Status } from 'src/app/types/catalogs.types';
import { Turn } from 'src/app/types/turn.types';
import { Material } from 'src/app/types/material.types';

export interface MaterialRequestPageable extends Pageable<MaterialRequest> {
}

export interface MaterialRequest {
  id: number;
  number: string;
  date: Date;
  documentByUsername: string;
  documentBy: string;
  status: Status;
  turn: Turn;
}

export interface MaterialRequestDetail {
  id: number;
  balance: number;
  coat: number;
  pallets: number;
  weight: number;
  consolidated: number;
  coatNumber: number;
  palletNumber: number;
  observation: string;
  material: Material;
}
