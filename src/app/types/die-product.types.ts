import { Status } from "./catalogs.types";

export interface DieProduct {
  id: number;
  code: string;
  name: string;
  area: number;
  length: number;
  width: number;
  gsmdis: number;
  status: Status;
}

export interface CreateDieProduct extends Omit<DieProduct, 'id' | 'status'> {
}

export interface UpdateDieProduct extends Omit<DieProduct, 'id' | 'status'> {
}
