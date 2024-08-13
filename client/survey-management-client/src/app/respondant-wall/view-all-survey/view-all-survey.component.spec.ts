import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewAllSurveyComponent } from './view-all-survey.component';

describe('ViewAllSurveyComponent', () => {
  let component: ViewAllSurveyComponent;
  let fixture: ComponentFixture<ViewAllSurveyComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ViewAllSurveyComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ViewAllSurveyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
