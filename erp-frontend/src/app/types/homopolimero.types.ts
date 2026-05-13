export interface Homopolimero {
  id: number;
  percentage: number;
  hpCode: string;
}

export interface CreateHomopolimero extends Omit<Homopolimero, 'id'> {
}

export interface UpdateHomopolimero extends Omit<Homopolimero, 'id'> {
}
