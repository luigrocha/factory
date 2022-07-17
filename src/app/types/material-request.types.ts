import {Pageable} from 'src/app/types/pageable.types';
import {Status} from 'src/app/types/catalogs.types';
import {Turn} from 'src/app/types/turn.types';
import {Material} from 'src/app/types/material.types';

export interface MaterialRequestPageable extends Pageable<MaterialRequestRes> {
}

export interface MaterialRequestRes {
  id: number;
  number: string;
  date: Date;
  documentByUsername: string;
  documentBy: string;
  status: Status;
  turn: Turn;
}

export interface MaterialRequest {
  material: Material;
  quantity: number;
  balance?: number;
  coat?: number;
  pallets?: number;
  weight?: number;
  observation?: string;
}

export interface MaterialRequestDetail {
  product: string;
  quantity: number;
  stopMp: number;
  necessary: number;
  request: number;
}

export interface Consolidated {
  material?: Material;
  product: string;
  quantity: number;
}

export interface MaterialRequestCreate{
  number: string;
  date: Date;
  documentByUsername: string;
  documentBy: string;
  status: string;
  turn: number;
}
