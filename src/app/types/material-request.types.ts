export interface MaterialRequest {
  id: number;
}

export interface MaterialRequestDetail {
  product: string;
  quantity: number;
  stopMp: number;
  necessary: number;
  request: number;
}

export interface Consolidated {
  product: string;
  quantity: number;
}
