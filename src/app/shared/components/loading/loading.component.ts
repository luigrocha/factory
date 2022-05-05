import { Component } from '@angular/core';
import { LoaderService } from 'src/app/core/services/loader.service';

@Component({
  selector: 'app-loading',
  templateUrl: './loading.component.html',
  styleUrls: ['./loading.component.scss']
})
export class LoadingComponent {

  loading: boolean = false;

  constructor(private loaderService: LoaderService) {
    this.loaderService.isLoading$
      .subscribe((isLoading) => {
        this.loading = isLoading;
      });
  }
}
