import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ProfileRoutingModule } from './profile-routing.module';
import { UserProfileComponent } from './pages/user-profile/user-profile.component';
import { GalleriaModule } from 'primeng/galleria';
import { ButtonModule } from 'primeng/button';
import { AvatarModule } from 'primeng/avatar';
import { TabMenuModule } from 'primeng/tabmenu';
import { MyProfileComponent } from './pages/my-profile/my-profile.component';
import { MyActivitiesComponent } from './pages/my-activities/my-activities.component';
import { CardModule } from 'primeng/card';
import { ProfileLineComponent } from './components/profile-line/profile-line.component';
import { DividerModule } from 'primeng/divider';
import { TimelineModule } from 'primeng/timeline';
import { ChipModule } from 'primeng/chip';
import { SharedModule } from 'src/app/shared/shared.module';


@NgModule({
  declarations: [
    UserProfileComponent,
    MyProfileComponent,
    MyActivitiesComponent,
    ProfileLineComponent
  ],
  imports: [
    CommonModule,
    ProfileRoutingModule,
    GalleriaModule,
    ButtonModule,
    AvatarModule,
    TabMenuModule,
    CardModule,
    DividerModule,
    TimelineModule,
    ChipModule,
    SharedModule
  ]
})
export class ProfileModule { }
