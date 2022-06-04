import { Component, OnInit } from '@angular/core';
import { Config } from 'src/app/types/config.types';
import { LayoutService } from 'src/app/core/services/layout.service';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.scss']
})
export class FooterComponent implements OnInit{

  config: Config;

  constructor(
    private layoutService: LayoutService,
  ) { }

  ngOnInit(): void {
    this.layoutService.config$
      .subscribe(config => {
        this.config = config;
      });
  }
}
