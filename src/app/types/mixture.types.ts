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
  prepare: number;
  total: number;
  observation?: string;
  rows: MixtureDetailCreate[];
}
