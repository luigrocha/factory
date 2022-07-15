import { Directive, ElementRef, Input, OnInit } from '@angular/core';
import { Status } from 'src/app/types/catalogs.types';

@Directive({
  selector: '[appStatusHighlight]'
})
export class StatusHighlightDirective implements OnInit {

  @Input() status: Status;

  constructor(
    private elementRef: ElementRef
  ) {
  }

  ngOnInit(): void {
    const defaultColor = this.elementRef.nativeElement.style.color;
    const defaultBackgroundColor = this.elementRef.nativeElement.style.backgroundColor;

    if (this.status) {
      this.elementRef.nativeElement.style.color = this.status.color || defaultColor;
      this.elementRef.nativeElement.style.backgroundColor = this.status.backgroundColor || defaultBackgroundColor;
    }
  }
}
