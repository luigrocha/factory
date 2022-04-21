import { RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { AppMainComponent } from './app.main.component';
import { AppNotfoundComponent } from './pages/app.notfound.component';
import { AppErrorComponent } from './pages/app.error.component';
import { AppAccessdeniedComponent } from './pages/app.accessdenied.component';
import { AppLoginComponent } from './pages/app.login.component';


@NgModule({
    imports: [
        RouterModule.forRoot([
            { path: '', component: AppMainComponent, },
            { path: 'error', component: AppErrorComponent },
            { path: 'access', component: AppAccessdeniedComponent },
            { path: 'notfound', component: AppNotfoundComponent },
            { path: 'login', component: AppLoginComponent },
            { path: '**', redirectTo: '/notfound' },
        ], { scrollPositionRestoration: 'enabled' })
    ],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
