import { Component, OnInit, ViewChild } from '@angular/core';
import { Table } from '@fullcalendar/daygrid';
import { PrimeNGConfig } from 'primeng/api';
import { User } from 'src/app/types/user.types';
import { UsersService } from './users.service';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.scss']
})
export class UsersComponent implements OnInit {

  users: User[];


  statuses: any[];

  loading: boolean = true;

  @ViewChild('dt') table: Table;

  constructor(private userService: UsersService, private primengConfig: PrimeNGConfig) { }

  ngOnInit() {
    this.userService.getAllUsers().subscribe(users => {
      this.users = users;
      this.loading = false;
    });
    this.primengConfig.ripple = true;
  }


}
