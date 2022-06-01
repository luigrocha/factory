import { ShortDie } from 'src/app/types/dies.types';
import { ColorB } from 'src/app/types/colorB.types';
import { Pageable } from 'src/app/types/pageable.types';

export interface CirelPageable extends Pageable<Cirel> {
}

export interface Cirel {
  id: number;
  print: string;
  description: string;
  description2: string;
  observation: string;
  die: ShortDie;
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
}
