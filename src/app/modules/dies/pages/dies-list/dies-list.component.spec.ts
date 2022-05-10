import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DiesListComponent } from './dies-list.component';

describe('DiesListComponent', () => {
  let component: DiesListComponent;
  let fixture: ComponentFixture<DiesListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DiesListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DiesListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
