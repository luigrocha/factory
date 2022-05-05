import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LayoutComponent } from './layout.component';
import { TopbarModule } from 'src/app/layout/common/topbar/topbar.module';
import { RightpanelModule } from 'src/app/layout/common/rightpanel/rightpanel.module';
import { MenuModule } from 'src/app/layout/common/menu/menu.module';
import { RouterModule } from '@angular/router';
import { BreadcrumbModule } from 'src/app/layout/common/breadcrumb/breadcrumb.module';
import { FooterModule } from 'src/app/layout/common/footer/footer.module';
import { ConfigModule } from 'src/app/layout/common/config/config.module';
import { SharedModule } from 'src/app/shared/shared.module';


@NgModule({
  declarations: [
    LayoutComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    TopbarModule,
    RightpanelModule,
    MenuModule,
    BreadcrumbModule,
    FooterModule,
    ConfigModule,
    SharedModule
  ]
})
export class LayoutModule { }
