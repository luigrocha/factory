import {Component, OnInit} from '@angular/core';
import {AppBreadcrumbService} from '../app.breadcrumb.service';
import {MenuItem} from 'primeng/api';

@Component({
    templateUrl: './widgets.component.html'
})
export class WidgetsComponent implements OnInit{

    likeChart: any;

    likeOptions: any;

    ordersChart: any;

    ordersOptions: any;

    activeOrders = 0;

    items: MenuItem[];

    val1 = 1;

    val2 = 2;

    constructor(private breadcrumbService: AppBreadcrumbService) {
        this.breadcrumbService.setItems([
            { label: 'Utilities' },
            { label: 'Widgets', routerLink: ['/utilities/widgets'] }
        ]);
    }

    ngOnInit() {
        this.ordersChart = {
            labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September'],
            datasets: [{
                label: 'Revenue',
                data:     [31, 83, 69, 29, 62, 25, 59, 26, 46],
                borderColor: [
                    '#00acac',
                ],
                borderWidth: 2,
                fill: false,
                borderDash: [3, 6],
                tension: .4
            }, {
                label: 'Cost',
                data:     [67, 98, 27, 88, 38, 3, 22, 60, 56],
                borderColor: [
                    '#f1b263',
                ],
                backgroundColor: [
                    'rgba(241, 178, 99, .07)',
                ],
                borderWidth: 2,
                fill: true,
                pointRadius: 3,
                tension: .4
            }]
        };

        this.ordersOptions = {
            plugins: {
                legend: {
                    display: true,
                    labels: {
                        color: '#A0A7B5'
                    }
                }
            },
            responsive: true,
            hover: {
                mode: 'index'
            },
            scales: {
                y: {
                    ticks: {
                        color: '#A0A7B5'
                    },
                    grid: {
                        color:  'rgba(160, 167, 181, .3)',
                    }
                },
                x: {
                    ticks: {
                        color: '#A0A7B5'
                    },
                    grid: {
                        color:  'rgba(160, 167, 181, .3)',
                    }
                },
            }
        };

        this.likeChart = {
            labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July'],
            datasets: [{
                label: 'Like',
                data: [20, 40, 20, 70, 20, 49, 30],
                backgroundColor: '#f16383',
                borderWidth: 0,
                fill: false,
            }, {
                label: 'Dislike',
                data: [5, 19, 10, 10, 22, 14, 10],
                backgroundColor: '#f1b263',
                borderWidth: 0,
                fill: false,
            },
                {
                    label: 'View',
                    data: [45, 80, 70, 90, 30, 90, 50],
                    backgroundColor: '#02acac',
                    borderWidth: 0,
                    fill: false,
                }]
        };

        this.likeOptions = {
            plugins: {
                legend: {
                    display: true,
                    labels: {
                        color: '#c3ccdd'
                    }
                }
            },
            responsive: true,
            hover: {
                mode: 'index'
            },
            scales: {
                x: {
                    display: false,
                    stacked: true,
                },
                y: {
                    display: false,
                    stacked: true
                }
            }
        };

        this.items = [
            {label: 'View Profile', icon: 'pi pi-user'},
            {label: 'Update Profile', icon: 'pi pi-refresh'},
            {label: 'Delete Profile', icon: 'pi pi-trash'},
        ];
    }

    changeDataset(event) {
        const dataSet = [
            [31, 83, 69, 29, 62, 25, 59, 26, 46],
            [40, 29, 7, 73, 81, 69, 46, 21, 96],
        ];
        const dataSet2 = [
            [67, 98, 27, 88, 38, 3, 22, 60, 56],
            [74, 67, 11, 36, 100, 49, 34, 56, 45],
        ];

        this.activeOrders = parseInt(event.currentTarget.getAttribute('data-index'));

        this.ordersChart.datasets[0].data = dataSet[parseInt(event.currentTarget.getAttribute('data-index'))];
        this.ordersChart.datasets[1].data = dataSet2[parseInt(event.currentTarget.getAttribute('data-index'))];
        this.ordersChart.datasets[0].label = event.currentTarget.getAttribute('data-label');
        this.ordersChart.datasets[0].borderColor = event.currentTarget.getAttribute('data-stroke');
    }
}
