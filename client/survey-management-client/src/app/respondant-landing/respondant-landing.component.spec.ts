import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RespondantLandingComponent } from './respondant-landing.component';

describe('RespondantLandingComponent', () => {
  let component: RespondantLandingComponent;
  let fixture: ComponentFixture<RespondantLandingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RespondantLandingComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(RespondantLandingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
