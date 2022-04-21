import { Component } from '@angular/core';
import { AppComponent } from '../../app.component';
import { HomeComponent } from '../../pages/home/home.component';

@Component({
    selector: 'app-topbar',
    templateUrl: './app.topbar.component.html'
})
export class AppTopBarComponent {

    constructor(public appMain: HomeComponent, public app: AppComponent) {
    }

}
