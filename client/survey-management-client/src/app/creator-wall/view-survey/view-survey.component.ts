import { AddVersionModalComponent } from './add-version-modal/add-version-modal.component';
import { AddSectionModalComponent } from './add-section-modal/add-section-modal.component';
import { CreatorService } from './../../core/service/creator.service';
import { RespondantService } from './../../core/service/respondant.service';
import { ActivatedRoute } from '@angular/router';
import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-view-survey',
  templateUrl: './view-survey.component.html',
  styleUrl: './view-survey.component.css'
})
export class ViewSurveyComponent {

  surveyId!: number;
  survey!:any;
  selectedVersion!:any;

  constructor(private route: ActivatedRoute, 
    private creatorService:CreatorService,
    public dialog: MatDialog) {}

  ngOnInit(): void {
    // Subscribe to the params observable
    this.route.params.subscribe(params => {
      this.surveyId = params['id'];
      this.fetchSurvey(this.surveyId)
    });
  }


  fetchSurvey(surveyId:number){
    this.creatorService.viewSurvey(surveyId).subscribe({
      next:(data)=>{
        this.survey = data;
        // console.log(this.survey)
      },
      error:(error)=>{

      }
    })
  }

  viewVersion(version:any){
    // console.log(version)
    
    this.selectedVersion = version;

  }

  scrollToDiv(divId: string): void {
    const element = document.getElementById(divId);
    if (element) {
      element.scrollIntoView({ behavior: 'smooth', block: 'start' });
    }
  }





  addVersionModal(){
    const dialogRef = this.dialog.open(AddVersionModalComponent, {
      data: {
        surveyId: this.surveyId,
      },
      width: '45%'
    });

    dialogRef.afterClosed().subscribe(result => {
      // Check if there is any data returned from the modal
      if (result && result.refreshed) {
        this.fetchSurvey(this.surveyId)
        // this.goToPage(this.currentPage)
        // this.idInput.nativeElement.value = ""
      }
    });
  }

  handleRefreshVersion(){
    this.fetchSurvey(this.surveyId);
    let temp =this.selectedVersion;
    this.selectedVersion = undefined;
    this.selectedVersion = temp;

    
  }




}
