/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { ColorAComponent } from './color-a.component';

describe('ColorAComponent', () => {
  let component: ColorAComponent;
  let fixture: ComponentFixture<ColorAComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ColorAComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ColorAComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
