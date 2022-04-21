import { Component } from '@angular/core';
import { AppBreadcrumbService } from '../../app.breadcrumb.service';

@Component({
    templateUrl: './documentation.component.html',
    styles: [`
        :host ::ng-deep .language-css .token.string {
            background: var(--surface-overlay);
            color: var(--text-white);
        }
        :host ::ng-deep .language-css .token.operator {
            background: var(--surface-overlay);
            color: var(--text-white);
        }
    `]
})
export class DocumentationComponent {

    constructor(private breadcrumbService: AppBreadcrumbService) {
        this.breadcrumbService.setItems([
            { label: 'Documentation', routerLink: ['/documentation'] }
        ]);
    }
}