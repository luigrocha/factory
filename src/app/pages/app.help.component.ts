import { Component } from '@angular/core';
import {AppBreadcrumbService} from '../app.breadcrumb.service';

@Component({
    templateUrl: './app.help.component.html',
})
export class AppHelpComponent {
    text: any;

    filteredText: any[];

    constructor(private breadcrumbService: AppBreadcrumbService) {
        this.breadcrumbService.setItems([
            {label: 'Pages'},
            {label: 'Help Page'}
        ]);
    }

    filterText(event) {
        const query = event.query;
        this.filteredText = [];

        for (let i = 0; i < 10; i++) {
            this.filteredText.push(query + i);
        }
    }

}
