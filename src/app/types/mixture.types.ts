import {Order} from './order.types';
import {Die} from './dies.types';
import {Material} from './material.types';

export interface MixtureShort{
  number?: number;
  mixture?: string;
  order?: Order;
}

export interface MixtureDetail {
  type?: string;
  product?: string;
  percent?: number;
  stop?: number;
  total?: number;
}

export interface MixtureDetailCreate {
  mixture: number;
  material: number;
  percent: number;
  stop: number;
  total: number;
}

export interface MixtureCreate{
  id?: number;
  order: number;
  number: number;
  documentTo: string;
  documentBy: string;
  date: Date;
  mixture: string;
  prepare: number;
  preMixture: number;
  total: number;
  totalReal: number;
  die: number;
  observation?: string;
  rows: MixtureDetailCreate[];
}

export interface MixtureDetailRes{
  percent: number;
  stop: number;
  total: number;
  material: Material;
}

export interface MixtureRes{
  id: number;
  prepare: number;
  preMixture: number;
  observation: string;
  number: number;
  documentBy: string;
  documentTo: number;
  mixture: string;
  total: number;
  totalReal: number;
  date: Date;
  die: Die;
  order: Order;
  rows: MixtureDetailRes[];
}
