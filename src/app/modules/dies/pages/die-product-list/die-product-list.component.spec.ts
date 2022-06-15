import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DieProductListComponent } from './die-product-list.component';

describe('DieProductListComponent', () => {
  let component: DieProductListComponent;
  let fixture: ComponentFixture<DieProductListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DieProductListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DieProductListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
