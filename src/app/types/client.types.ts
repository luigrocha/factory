export interface Client {
  id?: string;
  name?: string;
  imageUrl?: string;
  imageName?: string;
}

export interface CreateClient {
  id: string;
  name: string;
  file?: File;
}
