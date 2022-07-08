export interface Thickness {
  id: number;
  weight: number;
  thick: number;
}

export interface CreateThickness extends Omit<Thickness, 'id'> {
}

export interface UpdateThickness extends Omit<Thickness, 'id'> {
}
