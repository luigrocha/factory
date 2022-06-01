import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-profile-line',
  templateUrl: './profile-line.component.html',
  styleUrls: ['./profile-line.component.scss']
})
export class ProfileLineComponent implements OnInit {
  @Input() label: string;
  @Input() value: string;
  @Input() icon: string;

  constructor() { }

  ngOnInit(): void {
  }
}
