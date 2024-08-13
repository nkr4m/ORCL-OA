import { AddSectionModalComponent } from './../view-survey/add-section-modal/add-section-modal.component';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { CreatorService } from './../../core/service/creator.service';
import { Component } from '@angular/core';
import { AddSurveyModalComponent } from './add-survey-modal/add-survey-modal.component';

@Component({
  selector: 'app-creator-dashboard',
  templateUrl: './creator-dashboard.component.html',
  styleUrl: './creator-dashboard.component.css'
})
export class CreatorDashboardComponent {



  surveyList!:any;
  isSurveyDtlsAvailable!:boolean;
  currentPage!:any;
  totalPage!:any;
  totalElements!:any;

  isPrevDisabled:any = true;
  isNextDisabled:any = true;
  isSurveyAvailable:boolean = false;
  

  constructor(private creatorService:CreatorService, private router:Router, public dialog: MatDialog){
  }

  ngOnInit(){
    this.fetchSurveyPage()
  }

  fetchSurveyPage(pageNumber:number = 0, pageSize:number = 10){
    this.creatorService.viewAllSurvey(pageNumber, pageSize).subscribe({
      next:(data:any)=>{
        // console.log(data)
        this.surveyList = data.content
        this.currentPage = data.number
        this.totalPage = data.totalPages
        data.first == true ? this.isPrevDisabled = true : this.isPrevDisabled = false;
        data.last == true ? this.isNextDisabled = true : this.isNextDisabled = false;
        // console.log(this.totalPage, this.currentPage + 1)
        this.totalElements = data.totalElements

        if(this.totalElements > 0){
          this.isSurveyAvailable = true;
        }

      },
      error:(error)=>{

      }
    })
  }

  goToPage(pageNumber:any, pageSize:any){
    this.fetchSurveyPage(pageNumber, pageSize)
  }

  routeToSpecificSurvey(surveyId: number) {
    // Use absolute path to ensure it matches the route correctly
    this.router.navigate(['./creator-wall/survey', surveyId]);

  }

  openCreateSurveyModal() {
    const dialogRef = this.dialog.open(AddSurveyModalComponent, {
      width: '45%'
    });

    dialogRef.afterClosed().subscribe(result => {
      // Check if there is any data returned from the modal
      if (result && result.refreshed) {
        this.fetchSurveyPage()
      }
    });
  }

}
