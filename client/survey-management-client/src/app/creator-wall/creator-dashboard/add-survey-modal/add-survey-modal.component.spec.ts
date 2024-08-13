import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddSurveyModalComponent } from './add-survey-modal.component';

describe('AddSurveyModalComponent', () => {
  let component: AddSurveyModalComponent;
  let fixture: ComponentFixture<AddSurveyModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AddSurveyModalComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AddSurveyModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
