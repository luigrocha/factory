/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { StoreTm5Component } from './store-tm5.component';

describe('StoreTm5Component', () => {
  let component: StoreTm5Component;
  let fixture: ComponentFixture<StoreTm5Component>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StoreTm5Component ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StoreTm5Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
