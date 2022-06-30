import { Material } from './material.types';

export interface Celler {
  id?: number;
  numberDocument?: string;
  date?: Date;
  dateDocument?: Date;
  reason?: string;
  observation?: string;
  observations?: string;
  origin?: string;
  state?: boolean;
  destiny?: string;
}

export interface CellerDetail {
  id?: number;
  lote?: string;
  amount?: number;
  balance?: number;
  coat?: number;
  pallets?: number;
  weight?: number;
  celler?: Celler;
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

export interface LoteCeller {
  id?: number;
  lote?: string;
  location: Location;
}

export interface LocationStock {
  location?: Location;
  stock?: number;
}

export interface Stock {
  material?: Material;
  lote?: string;
  locationStock?: LocationStock[];
  stock?: number;
}

export interface TypeMaterialStock {
  id?: number;
  name?: string;
  stock?: number;
}

export enum DocumentEnum {
  CIB = 1,
  CEB = 2,
  MOV = 3,
  TM1 = 4,
  TM5 = 5,
  CEP = 6,
}

export interface OptionDocument {
  id?: number;
  name?: string;
  description?: string;
  document?: Document;
}

export interface GenerateReceipt {
  numberDocument: string;
  date?: Date;
  dateDocument?: Date;
  reason?: string;
  observation?: string;
  observations?: string;
  origin?: string;
  destiny?: string;
  deliveredBy?: string;
  receivedBy?: string;
  cellerItems: GenerateReceiptItem[];
}

export interface GenerateReceiptItem {
  material: number;
  lote: string;
  amount: number;
  balance: number;
  coat: number;
  pallets: number;
  weight: number;
  document?: number;
  location: number;
}
