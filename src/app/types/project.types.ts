import { Client } from 'src/app/types/client.types';
import { Thickness } from 'src/app/types/thickness.types';
import { Homopolimero } from 'src/app/types/homopolimero.types';
import { Talc } from 'src/app/types/talc.types';
import { Carbonate } from 'src/app/types/co3.types';
import { ColorB } from 'src/app/types/colorB.types';

export interface Project {
  id: number;
  client: Client;
  name: string;
  code: string;
  gsm: Thickness;
  homoPolymer: Homopolimero;
  talc: Talc;
  carbonate: Carbonate;
  aydProcess: boolean;
  color1: ColorB;
  isStructural: boolean;
  hasCrown: boolean;
}
