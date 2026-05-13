import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfileLineComponent } from './profile-line.component';

describe('ProfileLineComponent', () => {
  let component: ProfileLineComponent;
  let fixture: ComponentFixture<ProfileLineComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProfileLineComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ProfileLineComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
