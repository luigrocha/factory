/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { HomopolimerosComponent } from './homopolimeros.component';

describe('HomopolimerosComponent', () => {
  let component: HomopolimerosComponent;
  let fixture: ComponentFixture<HomopolimerosComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HomopolimerosComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HomopolimerosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
