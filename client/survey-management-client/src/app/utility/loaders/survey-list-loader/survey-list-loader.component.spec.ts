import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SurveyListLoaderComponent } from './survey-list-loader.component';

describe('SurveyListLoaderComponent', () => {
  let component: SurveyListLoaderComponent;
  let fixture: ComponentFixture<SurveyListLoaderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SurveyListLoaderComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SurveyListLoaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
