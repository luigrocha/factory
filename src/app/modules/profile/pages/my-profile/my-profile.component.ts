import { Component, OnInit } from '@angular/core';
import { PersonService } from 'src/app/core/http/persons/person.service';
import { Profile } from 'src/app/types/profile.types';

@Component({
  selector: 'app-my-profile',
  templateUrl: './my-profile.component.html',
  styleUrls: ['./my-profile.component.scss']
})
export class MyProfileComponent implements OnInit {

  profile: Profile;

  constructor(
    private personService: PersonService
  ) { }

  ngOnInit(): void {
    this.personService.getMyProfile()
      .subscribe(profile => {
        this.profile = profile;
      });
  }
}
