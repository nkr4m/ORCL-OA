import { NotificationService } from './../../../core/service/notification.service';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { CreatorService } from './../../../core/service/creator.service';
import { Component, Inject } from '@angular/core';

@Component({
  selector: 'app-add-version-modal',
  templateUrl: './add-version-modal.component.html',
  styleUrl: './add-version-modal.component.css'
})
export class AddVersionModalComponent {

  constructor(private creatorService:CreatorService,
    @Inject(MAT_DIALOG_DATA) public data: any, public dialogRef: MatDialogRef<AddVersionModalComponent>,
    private notifier:NotificationService){

  }

  description!:any;
  versionTitle!:any;

  saveVersion(){

    if(this.description == undefined || this.versionTitle == undefined || this.description == "" || this.versionTitle == ""){
      this.notifier.emitNotification('error', 'All the fields are mandatory', 'All the fields are mandatory to create a version')
      return;
    }


    let body:any = {
      "description": this.description,
      "versionTitle":this.versionTitle
    }

    this.creatorService.addVersion(body, this.data.surveyId).subscribe({
      next:(data)=>{
        this.notifier.emitNotification('success', 'Version Created', 'Version was successfully created!')
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
