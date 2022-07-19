import { Component, OnInit } from '@angular/core';
import { Config } from 'src/app/types/config.types';
import { LayoutService } from 'src/app/core/services/layout.service';
import { footerConfig } from 'src/app/core/constants/footer';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.scss']
})
export class FooterComponent implements OnInit{

  config: Config;

  proveedor: string;
  client: string;
  message: string;
  desc: string;
  date: Date;

  constructor(
    private layoutService: LayoutService,
  ) { 
    this.proveedor = footerConfig.proveedor;
    this.client = footerConfig.client;
    this.message = footerConfig.message;
    this.desc = footerConfig.desc;
    this.date = new Date();
  }

  ngOnInit(): void {
    this.layoutService.config$
      .subscribe(config => {
        this.config = config;
      });
  let re0 = '{0}'; 
  let year = '{date}'; 
  let re1 = '{1}'; 
  this.message = this.message.replace(re0, this.proveedor); 
  this.message = this.message.replace(re1, this.client); 
  this.message = this.message.replace(year, this.date.getFullYear().toString()); 
  }
}
