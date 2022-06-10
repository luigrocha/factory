import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MachineModalComponent } from './machine-modal.component';

describe('MachineModalComponent', () => {
  let component: MachineModalComponent;
  let fixture: ComponentFixture<MachineModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MachineModalComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MachineModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
