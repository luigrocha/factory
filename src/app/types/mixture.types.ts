import {Order} from './order.types';

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
