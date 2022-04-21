/* tslint:disable:no-unused-variable */

import { TestBed, async } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { AppComponent } from './app.component';
import { AppTopBarComponent } from './components/topbar/app.topbar.component';
import { AppFooterComponent } from './components/footer/app.footer.component';
import { AppMenuComponent } from './components/menu/app.menu.component';
import { ProgressBarModule } from 'primeng/progressbar';
import { MenuService } from './components/menu/app.menu.service';

describe('AppComponent', () => {
    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [RouterTestingModule, ProgressBarModule],
            declarations: [AppComponent,
                AppMenuComponent,
                AppTopBarComponent,
                AppFooterComponent
            ],
            providers: [MenuService]
        });
        TestBed.compileComponents();
    });

    it('should create the app', async(() => {
        const fixture = TestBed.createComponent(AppComponent);
        const app = fixture.debugElement.componentInstance;
        expect(app).toBeTruthy();
    }));
});
