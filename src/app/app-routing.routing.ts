import { RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';

import { LoginComponent } from './pages/login/login.component';
import { ErrorComponent } from './pages/error/error.component';
import { AccessDeniedComponent } from './pages/access-denied/access-denied.component';
import { NotFoundComponent } from './pages/not-found/not-found.component';
import { HomeComponent } from './pages/home/home.component';
import { CirelComponent } from './pages/home/cirel/cirel.component';
import { AuthGuard } from './auth/auth.guard';
import { UsersComponent } from './pages/home/users/users.component';


@NgModule({
    imports: [
        RouterModule.forRoot([
            { path: '', redirectTo: 'home', pathMatch: 'full' },
            { path: 'error', component: ErrorComponent },
            { path: 'access', component: AccessDeniedComponent },
            { path: 'notfound', component: NotFoundComponent },
            {
                path: 'home', component: HomeComponent,
                children: [
                    { path: 'cirel', component: CirelComponent },
                    { path: 'users', component: UsersComponent, canActivate: [AuthGuard], data: { roles: ['realm-admin'] } }
                ],
                canActivate: [AuthGuard],
                // data: { roles: ['realm-admin', 'realm-user'] }
            },
            { path: '**', redirectTo: '/notfound' },
        ], { scrollPositionRestoration: 'enabled' })
    ],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
