/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { StoreTm1Component } from './store-tm1.component';

describe('StoreTm1Component', () => {
  let component: StoreTm1Component;
  let fixture: ComponentFixture<StoreTm1Component>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StoreTm1Component ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StoreTm1Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
