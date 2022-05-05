import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';
import { LayoutComponent } from 'src/app/layout/layout.component';
import { Error404Component } from 'src/app/core/error/error404/error404.component';
import { Error403Component } from 'src/app/core/error/error403/error403.component';
import { Error500Component } from 'src/app/core/error/error500/error500.component';
import { AuthGuard } from 'src/app/core/auth/auth.guard';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'home',
    pathMatch: 'full'
  },
  {
    path: 'not-found',
    component: Error404Component
  },
  {
    path: 'access-denied',
    component: Error403Component
  },
  {
    path: 'error',
    component: Error500Component
  },
  {
    path: 'home',
    component: LayoutComponent,
    canActivate: [AuthGuard],
    children: [
      {
        path: 'usuarios',
        canActivate: [AuthGuard],
        data: { roles: ['realm-admin'] },
        loadChildren: () => import('./modules/users/users.module').then(m => m.UsersModule)
      },
      {
        path: 'roles',
        canActivate: [AuthGuard],
        data: { roles: ['realm-admin'] },
        loadChildren: () => import('./modules/roles/roles.module').then(m => m.RolesModule)
      },
      {
        path: 'troqueles',
        loadChildren: () => import('./modules/dies/dies.module').then(m => m.DiesModule)
      }
    ]
  },
  {
    path: '**',
    redirectTo: 'not-found'
  }
]

@NgModule({
  imports: [RouterModule.forRoot(routes, {
    scrollPositionRestoration: 'enabled'
  })],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
