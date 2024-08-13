import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RespondantWallComponent } from './respondant-wall.component';

describe('RespondantWallComponent', () => {
  let component: RespondantWallComponent;
  let fixture: ComponentFixture<RespondantWallComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RespondantWallComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(RespondantWallComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
