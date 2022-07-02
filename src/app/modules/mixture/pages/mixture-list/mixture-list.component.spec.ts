/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { MixtureListComponent } from './mixture-list.component';

describe('MixtureListComponent', () => {
  let component: MixtureListComponent;
  let fixture: ComponentFixture<MixtureListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MixtureListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MixtureListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
