import { NotificationService } from './../../core/service/notification.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { RespondantService } from './../../core/service/respondant.service';
import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
interface SurveyData {
  surveyId: number;
  versionId: number;
  data: any; // Adjust type based on your data structure
}

@Component({
  selector: 'app-view-survey',
  templateUrl: './view-survey.component.html',
  styleUrl: './view-survey.component.css'
})
export class ViewSurveyComponent {

  surveyId!: number;
  versionId!:any;

  survey!: any;
  selectedVersionId: any;
  
  extractedValues: any[] = [];

  surveyIdToFind = this.surveyId;
  matchingSurveyData: SurveyData[] = [];
  formSections!: any
  form!: FormGroup

  completeData !: any;

  constructor(private route: ActivatedRoute,
    private respondantService: RespondantService,
    private fb: FormBuilder, private notifier: NotificationService,
    private router:Router) { }

  ngOnInit(): void {
    // Subscribe to the params observable
    this.route.params.subscribe(params => {
      this.surveyId = params['id'];
      this.surveyIdToFind = params['id'];
      // this.fetchSurvey(this.surveyId)
    });

    this.form = this.fb.group({
    })

    this.findMatchingSurveyData()
  }


  fetchSurvey(surveyId: number) {
    this.respondantService.viewSurvey(surveyId).subscribe({
      next: (data) => {
        this.survey = data;
      },
      error: (error) => {

      }
    })
  }

  viewVersion(versionId:any) {
    // this.notifier.emitNotification('info', 'You have saved response for the survey', 'Please hit submit after completing your response to record.')
    // console.log(versionId, '----fetching old version, versionId ----')

    let version: any;

    this.respondantService.fetchSpVersion(this.surveyId, versionId).subscribe({
      next: (data: any) => {
        // console.log(data)
        this.completeData = data;
        version = data?.versions[0]
        this.selectedVersionId = versionId;
        this.formSections = version?.sections
        this.createForm()
      },
      error: (error) => {

      }
    })


  }

  viewLatestVersion() {
    // console.log('----fetching new version ----')
    let version: any;

    this.respondantService.fetchSpVersion2(this.surveyId).subscribe({
      next: (data: any) => {
        // console.log(data)
        this.completeData = data;
        version = data.versions[0]
        this.selectedVersionId = version.surveyVersionId
        this.formSections = version?.sections
        this.createForm()
      },
      error: (error) => {

      }
    })

  }

  scrollToDiv(divId: string): void {
    const element = document.getElementById(divId);
    if (element) {
      element.scrollIntoView({ behavior: 'smooth', block: 'start' });
    }
  }






  // survey version starts here

  findMatchingSurveyData() {
    for (let i = 0; i < localStorage.length; i++) {
      const key = localStorage.key(i);
      if (key) {
        const [surveyId, versionId] = this.extractSurveyIdAndVersionId(key);
        // console.log("SurveyId -> " + surveyId, ", VersionId ->" + versionId)
        this.versionId = versionId
        if (surveyId != undefined && surveyId != null && surveyId == this.surveyId) {
          // console.log(versionId, 'version to view')
          if(versionId == -1){
            continue
          }
          this.viewVersion(versionId);
          return;
        }
      }
    }
    this.viewLatestVersion()
  }


  //extracts version id and survey id 
  extractSurveyIdAndVersionId(key: string): [number, number] {
    const parts = key.split('__');
    if (parts.length === 2) {
      const surveyIdMatch = parts[0].match(/\d+/);
      const versionIdMatch = parts[1].match(/\d+/);
      const surveyId = surveyIdMatch ? parseInt(surveyIdMatch[0], 10) : -1;
      const versionId = versionIdMatch ? parseInt(versionIdMatch[0], 10) : -1;
      // this.checkIfOngoingVersion(surveyId, versionId)
      return [surveyId, versionId];
    }
    return [-1, -1];
  }


  createForm() {
    // Initialize empty object to hold form controls dynamically
    if (this.formSections == undefined) {
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
    this.checkOnLocalStorage()
  }

  onSubmit() {
    this.extractedValues = [];
    this.extractValues(this.form.value)
    this.respondantService.saveResponse(this.extractedValues, this.surveyId, this.selectedVersionId).subscribe({
      next: (data) => {
        this.notifier.emitNotification('success', 'Submitted Successfully', 'Survey was submitted successfully ')
        
        localStorage.removeItem(`ongoing-survey-${this.surveyId}__ongoing-version-${this.selectedVersionId}`)
        this.router.navigate(['respondant-wall']);
      },
      error: (error) => {

      }
    })

  }



  extractValues(obj: any) {
    for (let key in obj) {
      let numericPart = key.substring(10);
      if (typeof obj[key] === 'object') {
        //we have a checkbox
        let checkboxRes = ""
        for (let key2 in obj[key]) {
          if (obj[key][key2] == true) {
            checkboxRes += key2 + ","
          }
        }
        let temp = {
          "sectionId": numericPart,
          "response": checkboxRes
        }
        this.extractedValues.push(temp);
      } else {
        let temp = {
          "sectionId": numericPart,
          "response": obj[key] + ""
        }
        this.extractedValues.push(temp);
      }

    }
  }

  saveOnLocalStorage() {
    this.notifier.emitNotification('success', 'Saved to local storage', `Response is saved to the local storage, please not if the local storage is erased the data can't be reterived!`)
    localStorage.setItem(`ongoing-survey-${this.surveyId}__ongoing-version-${this.selectedVersionId}`, JSON.stringify(this.form.value))
  }

  checkOnLocalStorage() {
    let formVal: any = JSON.parse(localStorage.getItem(`ongoing-survey-${this.surveyId}__ongoing-version-${this.selectedVersionId}`));
    for (let key in formVal) {
      // Using computed property syntax to set the form value dynamically
      let patchObject = {};
      patchObject[key] = formVal[key];
      this.form.patchValue(patchObject);
    }
  }

}
