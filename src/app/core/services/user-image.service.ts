import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UserImageService {

  constructor() {
  }

  get userImage(): string {
    return sessionStorage.getItem('userImage');
  }

  setUserImage(image: string): void {
    sessionStorage.setItem('userImage', image);
  }
}
