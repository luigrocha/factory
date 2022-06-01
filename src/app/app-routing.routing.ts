import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';
import { LayoutComponent } from 'src/app/layout/layout.component';
import { Error404Component } from 'src/app/core/error/error404/error404.component';
import { Error403Component } from 'src/app/core/error/error403/error403.component';
import { Error500Component } from 'src/app/core/error/error500/error500.component';
import { AuthGuard } from 'src/app/core/auth/auth.guard';
import { RoleEnum } from './types/role.types';

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
        data: { roles: [RoleEnum.ADMIN] },
        loadChildren: () => import('./modules/users/users.module').then(m => m.UsersModule)
      },
      {
        path: 'roles',
        canActivate: [AuthGuard],
        data: { roles: [RoleEnum.ADMIN] },
        loadChildren: () => import('./modules/roles/roles.module').then(m => m.RolesModule)
      },
      {
        path: 'menu',
        canActivate: [AuthGuard],
        data: { roles: [RoleEnum.ADMIN] },
        loadChildren: () => import('./modules/menu/menu.module').then(m => m.MenuModule)
      },
      {
        path: 'catalogs',
        canActivate: [AuthGuard],
        data: { roles: [RoleEnum.SUPERVISOR] },
        loadChildren: () => import('./modules/catalogs/catalogs.module').then(m => m.CatalogsModule)
      },
      {
        path: 'troqueles',
        canActivate: [AuthGuard],
        data: { roles: [RoleEnum.USER] },
        loadChildren: () => import('./modules/dies/dies.module').then(m => m.DiesModule)
      },
      {
        path: 'cireles',
        canActivate: [AuthGuard],
        data: { roles: [RoleEnum.USER] },
        loadChildren: () => import('./modules/cireles/cireles.module').then(m => m.CirelesModule)
      },
      {
        path: 'clientes',
        canActivate: [AuthGuard],
        data: { roles: [RoleEnum.ADMIN] },
        loadChildren: () => import('./modules/clients/clients.module').then(m => m.ClientsModule)
      },
      {
        path: 'pedidos',
        canActivate: [AuthGuard],
        data: { roles: [RoleEnum.ADMIN] },
        loadChildren: () => import('./modules/order/order.module').then(m => m.OrderModule)
      },
      {
        path: 'bodega',
        canActivate: [AuthGuard],
        data: { roles: [RoleEnum.ADMIN] },
        loadChildren: () => import('./modules/store/store.module').then(m => m.StoreModule)
      },
      {
        path: 'proyectos',
        canActivate: [AuthGuard],
        data: { roles: [RoleEnum.ADMIN] },
        loadChildren: () => import('./modules/projects/projects.module').then(m => m.ProjectsModule)
      },
      {
        path: 'perfil',
        canActivate: [AuthGuard],
        loadChildren: () => import('./modules/profile/profile.module').then(m => m.ProfileModule)
      },
      {
        path: 'material',
        canActivate: [AuthGuard],
        data: { roles: [RoleEnum.SUPERVISOR] },
        loadChildren: () => import('./modules/material/material.module').then(m => m.MaterialModule)
      }
    ]
  },
  {
    path: '**',
    redirectTo: 'not-found'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {
    scrollPositionRestoration: 'enabled'
  })],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
