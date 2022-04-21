/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { CirelComponent } from './cirel.component';

describe('CirelComponent', () => {
  let component: CirelComponent;
  let fixture: ComponentFixture<CirelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CirelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CirelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
