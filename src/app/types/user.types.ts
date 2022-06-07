export interface User {
  id?: string;
  userName?: string;
  email?: string;
  firstName?: string;
  lastName?: string;
  roles?: Array<string>;
  password?: string;
}

export interface UserImage {
  imageUrl: string;
}
