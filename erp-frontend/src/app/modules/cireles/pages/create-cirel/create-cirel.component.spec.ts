import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateCirelComponent } from './create-cirel.component';

describe('CreateCirelComponent', () => {
  let component: CreateCirelComponent;
  let fixture: ComponentFixture<CreateCirelComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateCirelComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateCirelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
