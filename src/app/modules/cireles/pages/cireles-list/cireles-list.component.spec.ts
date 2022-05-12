/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { CirelesListComponent } from './cireles-list.component';

describe('CirelesListComponent', () => {
  let component: CirelesListComponent;
  let fixture: ComponentFixture<CirelesListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CirelesListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CirelesListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
