import { Component, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { BreadcrumbService } from 'src/app/core/services/breadcrumb.service';
import { PhotoService } from 'src/app/core/services/photo.service';
import { Image } from 'src/app/types/image.types';
import { MenuItem } from 'primeng/api';
import { TabMenu } from 'primeng/tabmenu';
import { PersonService } from 'src/app/core/http/persons/person.service';
import { UserImageService } from 'src/app/core/services/user-image.service';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss']
})
export class UserProfileComponent implements OnInit, OnChanges {
  images: Image[];
  items: MenuItem[];
  activeItem: MenuItem;
  tabIndex: number = 0;
  userImageUrl: string;

  responsiveOptions:any[] = [
    {
      breakpoint: '1024px',
      numVisible: 5
    },
    {
      breakpoint: '960px',
      numVisible: 4
    },
    {
      breakpoint: '768px',
      numVisible: 3
    },
    {
      breakpoint: '560px',
      numVisible: 1
    }
  ];

  constructor(
    private breadcrumbService: BreadcrumbService,
    private photoService: PhotoService,
    private imageUserService: UserImageService
  ) { }

  ngOnInit(): void {
    this.breadcrumbService.setItems([]);
    this.userImageUrl = this.imageUserService.userImage;
    this.items = [
      {
        label: 'Mi Perfil',
        icon: 'pi pi-fw pi-user',
        routerLink: '/home/perfil/mi-perfil'
      },
      {
        label: 'Actividades',
        icon: 'pi pi-fw pi-calendar',
        routerLink: '/home/perfil/actividades'
      },
    ];
    this.activeItem = this.items[0];
    this.photoService.getImages().then(images =>{
      this.images = images
    })
  }

  changeTab(tab: TabMenu) {
    this.activeItem = tab.activeItem;
  }

  consoleLog(event) {
    console.log(event);
  }

  ngOnChanges(changes: SimpleChanges): void {
    console.log(changes);
  }
}
