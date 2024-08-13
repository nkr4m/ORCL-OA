import { NotificationService } from './../../../core/service/notification.service';
import { CreatorService } from './../../../core/service/creator.service';
import { Component, Inject } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-add-section-modal',
  templateUrl: './add-section-modal.component.html',
  styleUrl: './add-section-modal.component.css'
})
export class AddSectionModalComponent {

  sectionForm:FormGroup;

  selectedSectionType!:any;

  showOptionSection:boolean = false;

  versionId!:any;

  constructor(public dialogRef: MatDialogRef<AddSectionModalComponent>, private fb:FormBuilder, 
    private creatorService:CreatorService, @Inject(MAT_DIALOG_DATA) public data: any, private notifier:NotificationService) { }
  ngOnInit(): void {

    this.initSectionForm()
    // console.log(this.data)
    
  }
  optionChange(e){
    this.selectedSectionType =  e.target.value
  }

  initSectionForm() {
    this.sectionForm = this.fb.group({
      sectionType: ["", Validators.required],
      sectionTitle: ["", Validators.required],
      sectionOptions: this.fb.array([this.createSectionOption()], Validators.required)
    });
  }
  
  createSectionOption(): FormGroup {
    return this.fb.group({
      option: ["", Validators.required]
    });
  }

  onSectionTypeChange(event: Event): void {
    const selectedType = (event.target as HTMLSelectElement).value;
    this.showOptionSection = selectedType === 'RADIO' || selectedType === 'CHECKBOX';
  }

  get sectionOptions(): FormArray {
    return this.sectionForm.get('sectionOptions') as FormArray;
  }
  
  addSectionOption() {
    this.sectionOptions.push(this.createSectionOption());
  }
  
  removeSectionOption(index: number) {
    if (this.sectionOptions.length > 1) {
      this.sectionOptions.removeAt(index);
    }
  }

  handleSave(){
    // console.log(this.data)
    this.creatorService.addSection(this.sectionForm.value, this.data.versionId.surveyVersionId).subscribe({
      next:(data)=>{
        this.notifier.emitNotification('success', 'Section Created', 'Section was successfully created!')
        // console.log(data)
        this.dialogRef.close({
          refreshed: true,
        })
      },
      error:(error)=>{

      }
    })
  }

}
