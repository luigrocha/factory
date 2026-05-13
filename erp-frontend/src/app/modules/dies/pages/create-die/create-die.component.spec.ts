import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateDieComponent } from './create-die.component';

describe('CreateDieComponent', () => {
  let component: CreateDieComponent;
  let fixture: ComponentFixture<CreateDieComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateDieComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateDieComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
