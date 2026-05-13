import { PersonGroup } from 'src/app/types/person-group.types';

export interface Ethnic {
  id: string;
  name: string;
}

export interface Gender {
  id: string;
  name: string;
}

export interface Person {
  id: string;
  ci: string;
  firstLastName: string;
  secondLastName: string;
  firstName: string;
  secondName: string;
  fullName: string;
  birthDate: Date;
  mobil1: string;
  mobil2: string;
  email1: string;
  email2: string;
  address: string;
  landline: string;
  contractDate: Date;
  observation: string;
  enforce: number;
  imageName: string;
  ethnic: Ethnic;
  gender: Gender;
  group: PersonGroup;
}

export interface ShortPerson {
  id: number;
  ci: string;
  firstLastName: string;
  secondLastName: string;
  firstName: string;
  secondName: string;
  fullName: string;
  email1: string;
}
