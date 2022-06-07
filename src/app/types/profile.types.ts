import { Person } from 'src/app/types/person.types';

export interface Profile {
  username: string;
  roles: Array<string>;
  person: Person;
}
