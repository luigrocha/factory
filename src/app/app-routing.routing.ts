import { RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';

import { LoginComponent } from './pages/login/login.component';
import { ErrorComponent } from './pages/error/error.component';
import { AccessDeniedComponent } from './pages/access-denied/access-denied.component';
import { NotFoundComponent } from './pages/not-found/not-found.component';
import { HomeComponent } from './pages/home/home.component';
import { CirelComponent } from './pages/home/cirel/cirel.component';


@NgModule({
    imports: [
        RouterModule.forRoot([
            { path: '', component: LoginComponent },
            { path: 'error', component: ErrorComponent },
            { path: 'access', component: AccessDeniedComponent },
            { path: 'notfound', component: NotFoundComponent },
            {
                path: 'home', component: HomeComponent,
                children: [{ path: 'cirel', component: CirelComponent }]
            },
            { path: '**', redirectTo: '/notfound' },
        ], { scrollPositionRestoration: 'enabled' })
    ],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
