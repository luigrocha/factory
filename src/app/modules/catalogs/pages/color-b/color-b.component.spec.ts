/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { ColorBComponent } from './color-b.component';

describe('ColorBComponent', () => {
  let component: ColorBComponent;
  let fixture: ComponentFixture<ColorBComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ColorBComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ColorBComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
