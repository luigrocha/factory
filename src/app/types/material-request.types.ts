import {Time} from '@angular/common';
import {Material} from './material.types';

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

export interface Turns {
  id: number;
  name: string;
  startTime: Time;
  endTime: Time;
  isActive: boolean;
}
