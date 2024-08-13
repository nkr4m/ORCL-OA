import { NotificationService } from './../../../core/service/notification.service';
import { MatDialogRef } from '@angular/material/dialog';
import { Component } from '@angular/core';
import { CreatorService } from '../../../core/service/creator.service';

@Component({
  selector: 'app-add-survey-modal',
  templateUrl: './add-survey-modal.component.html',
  styleUrl: './add-survey-modal.component.css'
})
export class AddSurveyModalComponent {

  surveyTitle!:any;
  surveyDesc!:any;

  constructor(private creatorService:CreatorService, 
    public dialogRef: MatDialogRef<AddSurveyModalComponent>, private notifier:NotificationService){

  }

  saveSurvey(){

    if(this.surveyTitle == undefined || this.surveyDesc == undefined || this.surveyTitle == "" || this.surveyDesc == ""){
      this.notifier.emitNotification('error', 'All the fields are mandatory', 'All the fields are mandatory to create a survey')
      return;
    }

    let body = {
      "title":this.surveyTitle,
      "description":this.surveyDesc
    }
    this.creatorService.addSurvey(body).subscribe({
      next:(data)=>{
        this.notifier.emitNotification('success', 'Survey Created', 'Survey was successfully created!')
        this.dialogRef.close({
          refreshed: true,
        })
      },
      error:(error)=>{
        // console.log(error);
      }
    })
  }

}
