export interface ColorCatalog {
  id: number;
  name: string;
  colorCode: string;
}

export interface CreateColorCatalog extends Omit<ColorCatalog, 'id'> {
}

export interface UpdateColorCatalog extends Omit<ColorCatalog, 'id'> {
}
