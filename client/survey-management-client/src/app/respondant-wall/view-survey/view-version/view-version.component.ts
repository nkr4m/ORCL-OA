import { RespondantService } from './../../../core/service/respondant.service';
import { CreatorService } from './../../../core/service/creator.service';
import { FormGroup, FormBuilder, Validators, FormArray } from '@angular/forms';
import { Component, Input, OnInit, OnChanges, SimpleChanges } from '@angular/core';
interface SurveyData {
  surveyId: number;
  versionId: number;
  data: any; // Adjust type based on your data structure
}

@Component({
  selector: 'app-view-version',
  templateUrl: './view-version.component.html',
  styleUrl: './view-version.component.css'
})
export class ViewVersionComponent {
  formSections!:any
  form!:FormGroup
  @Input() version!: any; // Input property to receive data
  @Input() surveyId!: any; // Input property to receive data
  @Input() versionId:any;
  extractedValues: any[] = [];

  surveyIdToFind = -1;
  matchingSurveyData: SurveyData[] = [];

  constructor(private fb:FormBuilder, private respondantService:RespondantService) { }

  ngOnInit(): void {
    const formGroup = {};
    this.createForm(); // Call method to create form on component initialization
    // console.log('Survey details on init:', this.version);
    this.surveyIdToFind = 1
    this.findMatchingSurveyData();
    // console.log('User ongoing:', this.matchingSurveyData);
  }


  findMatchingSurveyData() {
    for (let i = 0; i < localStorage.length; i++) {
      const key = localStorage.key(i);
      if (key) {
        if (this.isMatchingSurveyIdAndVersionId(key)) {
          const [surveyId, versionId] = this.extractSurveyIdAndVersionId(key);
          const data = JSON.parse(localStorage.getItem(key)!);
          this.matchingSurveyData.push({ surveyId, versionId, data });
        }
      }
    }
  }

  isMatchingSurveyIdAndVersionId(key: string): boolean {
    const [surveyId, versionId] = this.extractSurveyIdAndVersionId(key);
    return surveyId === this.surveyIdToFind;
  }

  extractSurveyIdAndVersionId(key: string): [number, number] {
    const parts = key.split('__');
    if (parts.length === 2) {
      const surveyIdMatch = parts[0].match(/\d+/);
      const versionIdMatch = parts[1].match(/\d+/);
      const surveyId = surveyIdMatch ? parseInt(surveyIdMatch[0], 10) : -1;
      const versionId = versionIdMatch ? parseInt(versionIdMatch[0], 10) : -1;
      return [surveyId, versionId];
    }
    return [-1, -1];
  }



  ngOnChanges(changes: SimpleChanges): void {


    if (changes['version']) {
      const currentValue = changes['version'].currentValue;
      const previousValue = changes['version'].previousValue;
      // console.log('Survey details changed:', { previousValue, currentValue });
      this.formSections = currentValue?.sections
      // console.log(this.formSections)
      // console.log(this.formSections)
      // console.log(this.surveyId, 'survey id')
      // console.log(this.versionId, 'version id')
      this.createForm()
      // Handle changes here, e.g., update local state based on new values
    }
  }



  createForm() {
     // Initialize empty object to hold form controls dynamically
    if(this.formSections == undefined){
      return;
    }
    const formGroup = {};
    this.formSections.forEach(section => {
      // Determine form control type based on sectionType
      if (section.sectionType === 'TEXTAREA') {
        formGroup[`sectionId-${section.sectionId}`] = this.fb.control('', Validators.required);
      } else if (section.sectionType === 'RADIO') {
        formGroup[`sectionId-${section.sectionId}`] = this.fb.control('', Validators.required);
      } else if (section.sectionType === 'CHECKBOX') {
        const checkboxOptions = section.sectionOptions.reduce((options, option) => {
          options[`${option.sectionOptionId}`] = this.fb.control(false);
          return options;
        }, {});
        formGroup[`sectionId-${section.sectionId}`] = this.fb.group(checkboxOptions);
      }
      // Additional cases for other section types can be added here
    });

    this.form = this.fb.group(formGroup); // Assign formGroup to this.form
    // console.log(this.form)
    this.checkOnLocalStorage()
  }

  onSubmit() {
    this.extractedValues = [];
      // console.log(this.form.value); // Access form data here
      this.extractValues(this.form.value)
      this.respondantService.saveResponse(this.extractedValues, this.surveyId, this.versionId).subscribe({
        next:(data)=>{
          // console.log(data)
        },
        error:(error)=>{

        }
      })
  
  }






  extractValues(obj: any) {
    for (let key in obj) {
      let numericPart = key.substring(10);
      if(typeof obj[key] === 'object'){
        //we have a checkbox
        let checkboxRes = ""
        for(let key2 in obj[key]){
          if(obj[key][key2] == true){
            checkboxRes += key2 + ","
          }
        }
        let temp = {
          "sectionId":numericPart,
          "response":checkboxRes
        }
        this.extractedValues.push(temp);
      }else{
        // console.log(numericPart, obj[key])
        let temp = {
          "sectionId":numericPart,
          "response":obj[key] + ""
        }
        this.extractedValues.push(temp);
      }

    }
  }

  saveOnLocalStorage(){
    localStorage.setItem(`ongoing-version-${this.versionId}__ongoing-section-${this.surveyId}`, JSON.stringify(this.form.value))
  }

  checkOnLocalStorage(){
    let formVal: any = JSON.parse(localStorage.getItem(`ongoing-version-${this.versionId}__ongoing-section-${this.surveyId}`));
    for(let key in formVal){
      // console.log(key, formVal[key])
      // Using computed property syntax to set the form value dynamically
      let patchObject = {};
      patchObject[key] = formVal[key];
      this.form.patchValue(patchObject);
    }
  }
  



  
}
