import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreatorWallComponent } from './creator-wall.component';

describe('CreatorWallComponent', () => {
  let component: CreatorWallComponent;
  let fixture: ComponentFixture<CreatorWallComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CreatorWallComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CreatorWallComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
