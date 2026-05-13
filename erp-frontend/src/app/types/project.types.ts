import { Client } from 'src/app/types/client.types';
import { Homopolimero } from 'src/app/types/homopolimero.types';
import { Talc } from 'src/app/types/talc.types';
import { CalciumCarbonate } from 'src/app/types/co3.types';
import { ColorB } from 'src/app/types/colorB.types';
import { ShortDieProduct } from 'src/app/types/die-product.types';
import { Cirel } from 'src/app/types/cirel.types';

export interface Project {
  id: number;
  name: string;
  codeGen: string;
  gsm: number;
  aydProcess: boolean;
  isStructural: boolean;
  hasCrown: boolean;
  leafName: string;
  referenceTag: string;
  hasLogo: boolean;
  quantityXPackage: number;
  client: Client;
  homoPolymer: Homopolimero;
  colorB: ColorB;
  cyrel: Cirel;
  talc: Talc;
  calciumCarbonate: CalciumCarbonate;
  dieProduct: ShortDieProduct;
  projectType: ProjectType;
}

export interface ProjectShort {
  id: number;
  name: string;
  codeGen: string;
}

export interface ProjectType {
  id: number;
  name: string;
  code: string;
}
