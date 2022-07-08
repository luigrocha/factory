import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ThicknessModalComponent } from './thickness-modal.component';

describe('ThicknessModalComponent', () => {
  let component: ThicknessModalComponent;
  let fixture: ComponentFixture<ThicknessModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ThicknessModalComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ThicknessModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
