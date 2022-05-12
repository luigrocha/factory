import { ChangeDetectorRef, Component, Input, OnDestroy, OnInit } from '@angular/core';
import { menuItemAnimation } from 'src/app/core/animations/menuitem.animation';
import { Subscription } from 'rxjs';
import { NavigationEnd, Router } from '@angular/router';
import { filter } from 'rxjs/operators';
import { MenuService } from 'src/app/core/services/menu.service';
import { LayoutComponent } from 'src/app/layout/layout.component';
import { TreeNode } from 'primeng/api';

@Component({
  selector: '[app-menuitem]',
  templateUrl: './menuitem.component.html',
  styleUrls: ['./menuitem.component.scss'],
  host: {
    '[class.layout-root-menuitem]': 'root || active',
    '[class.active-menuitem]': '(active)'
  },
  animations: [
    menuItemAnimation
  ]
})
export class MenuitemComponent implements OnInit, OnDestroy {


  @Input() item: any;

  @Input() index: number;

  @Input() root: boolean;

  @Input() parentKey: string;

  active = false;

  menuSourceSubscription: Subscription;

  menuResetSubscription: Subscription;

  key: string;

  constructor(
    public app: LayoutComponent,
    public router: Router,
    private cd: ChangeDetectorRef,
    private menuService: MenuService
  ) {
    this.menuSourceSubscription = this.menuService.menuSource$.subscribe(key => {
      // deactivate current active menu
      if (this.active && this.key !== key && key.indexOf(this.key) !== 0) {
        this.active = false;
      }
    });

    this.menuResetSubscription = this.menuService.resetSource$.subscribe(() => {
      this.active = false;
    });

    this.router.events.pipe(filter(event => event instanceof NavigationEnd))
      .subscribe(params => {
        if (this.app.isHorizontal()) {
          this.active = false;
        } else {
          if (this.item.routerLink) {
            this.updateActiveStateFromRoute();
          } else {
            this.active = false;
          }
        }
      });
  }

  ngOnInit() {
    if (!this.app.isHorizontal() && this.item.routerLink) {
      this.updateActiveStateFromRoute();
    }
    this.key = this.parentKey ? this.parentKey + '-' + this.index : String(this.index);
  }

  updateActiveStateFromRoute() {
    // this.active = this.router.isActive(this.item.routerLink[0], this.item.items ? false : true);
    if (this.item.items) {
      this.findRouter(this.item.items);
    } else {
      this.active = this.item.routerLink[0] === this.router.url ? true : false;
    }
  }

  findRouter(list: Array<any>) {
    list.forEach(item => {
      if (this.router.url === item.routerLink[0]) {
        this.active = true;
      } else if (item.items) {
        this.findRouter(item.items);
      }
    });
  }

  itemClick(event: Event) {
    // avoid processing disabled items
    if (this.item.disabled) {
      event.preventDefault();
      return;
    }

    // navigate with hover in horizontal mode
    if (this.root) {
      this.app.menuHoverActive = !this.app.menuHoverActive;
    }

    // notify other items
    this.menuService.onMenuStateChange(this.key);

    // execute command
    if (this.item.command) {
      this.item.command({ originalEvent: event, item: this.item });
    }

    // toggle active state
    if (this.item.items) {
      this.active = !this.active;
    } else {
      // activate item
      this.active = true;

      // reset horizontal menu
      if (this.app.isHorizontal()) {
        this.menuService.reset();
        this.app.menuHoverActive = false;
      }

      this.app.overlayMenuActive = false;
      this.app.staticMenuMobileActive = false;
    }
  }

  onMouseEnter() {
    // activate item on hover
    if (this.root && this.app.isHorizontal() && this.app.isDesktop()) {
      if (this.app.menuHoverActive) {
        this.menuService.onMenuStateChange(this.key);
        this.active = true;
      }
    }
  }

  ngOnDestroy() {
    if (this.menuSourceSubscription) {
      this.menuSourceSubscription.unsubscribe();
    }

    if (this.menuResetSubscription) {
      this.menuResetSubscription.unsubscribe();
    }
  }
}
