import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { BrowserModule } from '@angular/platform-browser';
import { HashLocationStrategy, LocationStrategy } from '@angular/common';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.routing';

import { FullCalendarModule } from '@fullcalendar/angular';

import { AppCodeModule } from './components/code/app.code.component';
import { AppComponent } from './app.component';

import { MenuService } from './components/menu/app.menu.service';
import { AppBreadcrumbService } from './components/breadcrumb/app.breadcrumb.service';

import dayGridPlugin from '@fullcalendar/daygrid';
import timeGridPlugin from '@fullcalendar/timegrid';
import interactionPlugin from '@fullcalendar/interaction';
import { LoginComponent } from './pages/login/login.component';
import { ErrorComponent } from './pages/error/error.component';
import { AccessDeniedComponent } from './pages/access-denied/access-denied.component';
import { NotFoundComponent } from './pages/not-found/not-found.component';
import { HomeModule } from './pages/home/home.module';

FullCalendarModule.registerPlugins([
    dayGridPlugin,
    timeGridPlugin,
    interactionPlugin
]);

@NgModule({
    imports: [
        BrowserModule,
        FormsModule,
        AppRoutingModule,
        HttpClientModule,
        BrowserAnimationsModule,
        AppCodeModule,
        HomeModule,
    ],
    declarations: [
        AppComponent,
        LoginComponent,
        ErrorComponent,
        AccessDeniedComponent,
        NotFoundComponent,
    ],
    providers: [
        { provide: LocationStrategy, useClass: HashLocationStrategy },
        MenuService, AppBreadcrumbService
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}
