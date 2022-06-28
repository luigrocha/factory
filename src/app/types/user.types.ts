import { Person } from 'src/app/types/person.types';

export interface User {
  id?: string;
  username?: string;
  email?: string;
  firstName?: string;
  lastName?: string;
  roles?: Array<string>;
  password?: string;
  person?: Person;
}

export interface CreateUser {
  username: string;
  email: string;
  firstName: string;
  lastName: string;
  password: string;
  personId: number;
}

export interface UpdateUser {
  email: string;
  firstName: string;
  lastName: string;
  personId: number;
}

export interface UserImage {
  imageUrl: string;
}

export interface GenerateUsername {
  firstName: string;
  lastName: string;
}

export interface GeneratedUsername {
  username: string;
}
