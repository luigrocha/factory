export interface Client {
  id?: number;
  code?: string;
  name?: string;
  imageUrl?: string;
  imageName?: string;
}

export interface CreateClient {
  code: string;
  name: string;
  file?: File;
}
