import { ColorB } from 'src/app/types/colorB.types';
import { Pageable } from 'src/app/types/pageable.types';
import { ShortDieProduct } from "./die-product.types";
import { ColorCatalog } from 'src/app/types/color-catalog.types';

export interface CirelPageable extends Pageable<Cirel> {
}

export interface Cirel {
  id: number;
  print: string;
  description: string;
  description2: string;
  observation: string;
  documentUrl: string;
  dies: ShortDieProduct[];
  printer: string;
  mbLeaf: ColorB;
  cyrelColors: CirelColor[];
}

export interface CirelColor {
  id: number;
  index: number;
  color: string;
  colorType: string;
  colorCode?: string;
  observation: string;
}

export interface CreateCirel {
  print: string;
  description: string;
  description2: string;
  observation: string;
  printerId: number;
  mbLeafId: number;
  dieProductIds: number[];
  cyrelColors: CirelColor[];
}

export interface CreateCirelColor {
  index: number;
  observation: string;
  color: ColorCatalog;
  colorType: string;
}
