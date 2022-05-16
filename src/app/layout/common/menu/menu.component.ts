import { Component, OnInit } from '@angular/core';
import { AppComponent } from 'src/app/app.component';
import { AuthService } from 'src/app/core/auth/service/auth.service';
import { MenuService } from 'src/app/core/http/menu/menu.service';

@Component({
    selector: 'app-menu',
    templateUrl: './menu.component.html',
    styleUrls: ['./menu.component.scss']
})
export class MenuComponent implements OnInit {

    model: any[];

    constructor(
        public app: AppComponent,
        private menuService: MenuService,
        private authService: AuthService
    ) { }

    get isAdmin(): boolean {
        return this.authService.isAdmin();
    }

    ngOnInit() {
        this.menuService.getAllItems().subscribe(
            (data => {
                this.model = data;
            })
        );
    }
}
