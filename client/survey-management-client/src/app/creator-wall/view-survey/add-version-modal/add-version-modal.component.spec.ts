import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddVersionModalComponent } from './add-version-modal.component';

describe('AddVersionModalComponent', () => {
  let component: AddVersionModalComponent;
  let fixture: ComponentFixture<AddVersionModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AddVersionModalComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AddVersionModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
