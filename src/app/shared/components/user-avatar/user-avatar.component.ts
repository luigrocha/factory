import { Component, Input, OnInit } from '@angular/core';
import { UserImageService } from 'src/app/core/services/user-image.service';

@Component({
  selector: 'app-user-avatar',
  templateUrl: './user-avatar.component.html',
  styleUrls: ['./user-avatar.component.scss']
})
export class UserAvatarComponent implements OnInit {
  @Input() styleClasses: string;
  @Input() ngClasses: string;
  userImageUrl: string;

  constructor(
    private userImageService: UserImageService
  ) { }

  ngOnInit(): void {
    this.userImageUrl = this.userImageService.userImage;
  }
}
