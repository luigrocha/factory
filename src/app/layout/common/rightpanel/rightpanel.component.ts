import { Component } from '@angular/core';
import { LayoutComponent } from 'src/app/layout/layout.component';

@Component({
  selector: 'app-rightpanel',
  templateUrl: './rightpanel.component.html',
  styleUrls: ['./rightpanel.component.scss']
})
export class RightpanelComponent {

  checked1 = true;

  checked2 = true;

  checked3 = false;

  checked4 = false;

  checked5 = false;

  checked6 = false;

  checked7 = false;

  checked8 = false;

  constructor(public app: LayoutComponent) {
  }

}
