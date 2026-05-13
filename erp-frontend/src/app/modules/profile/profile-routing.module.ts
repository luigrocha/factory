import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UserProfileComponent } from 'src/app/modules/profile/pages/user-profile/user-profile.component';
import { MyProfileComponent } from 'src/app/modules/profile/pages/my-profile/my-profile.component';
import { MyActivitiesComponent } from 'src/app/modules/profile/pages/my-activities/my-activities.component';

const routes: Routes = [
  {
    path: '',
    component: UserProfileComponent,
    children: [
      {
        path: 'mi-perfil',
        component: MyProfileComponent
      },
      {
        path: 'actividades',
        component: MyActivitiesComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProfileRoutingModule { }
