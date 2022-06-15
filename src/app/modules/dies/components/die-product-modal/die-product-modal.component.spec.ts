import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DieProductModalComponent } from './die-product-modal.component';

describe('DieProductModalComponent', () => {
  let component: DieProductModalComponent;
  let fixture: ComponentFixture<DieProductModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DieProductModalComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DieProductModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
