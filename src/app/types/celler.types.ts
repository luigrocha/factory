import { Material } from './material.types';

export interface Celler {
  id?: number;
  numberDocument?: string;
  lote?: string;
  amount?: number;
  balance?: number;
  coat?: number;
  pallets?: number;
  weight?: number;
  date?: Date;
  createdAt?: Date;
  observation?: string;
  material?: Material;
  location?: Location;
  document?: Document;
}

export interface Location {
  id?: number;
  location?: string;
  description?: string;
}

export interface Document {
  id?: number;
  name?: string;
  description?: string;
}

export interface CodeDocument {
  document?: string;
  number?: number;
  numDocument?: string;
}

export enum DocumentEnum {
  CIB = 1,
  CEB = 2,
  MOV = 3,
  TM3 = 4
}


export interface OptionDocument {
  id?: number;
  name?: string;
  description?: string;
  document?: Document;
}

export interface GenerateReceipt {
  receiptNumber: string;
  receiptDate: Date;
  reason: string;
  reasonObservation: string;
  observations: string;
  deliveredBy?: string;
  receivedBy?: string;
  items: GenerateReceiptItem[];
}

export interface GenerateReceiptItem {
  productType: string;
  productName: string;
  lot: string;
  units: number;
  bags1KG: number;
  bags25KG: number;
  pallets55: number;
  totalWeight: number;
  location: string;
}
