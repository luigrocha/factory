import { DieProduct } from "./die-product.types";
import { DocumentInfo } from 'src/app/types/document.types';

export interface Die {
  id: number;
  name: string;
  createdDate: Date;
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
  dieProduct: DieProduct;
  machines: string[];
  documents: DieDocument[];
}

export interface DieDocument extends DocumentInfo {
}

export interface CreateDie {
  name: string;
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
  manufacturerId: number;
  dieProductId: number;
  machines: number[];
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
