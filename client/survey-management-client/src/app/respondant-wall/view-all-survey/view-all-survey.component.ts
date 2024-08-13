import { RespondantService } from './../../core/service/respondant.service';
import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-view-all-survey',
  templateUrl: './view-all-survey.component.html',
  styleUrl: './view-all-survey.component.css'
})
export class ViewAllSurveyComponent {

  surveyList!:any;
  isSurveyDtlsAvailable!:boolean;
  currentPage!:any;
  totalPage!:any;
  totalElements!:any;

  isPrevDisabled:any = true;
  isNextDisabled:any = true;
  isSurveyAvailable:boolean = false;
  

  constructor(private respondantService:RespondantService, private router:Router){
  }

  ngOnInit(){
    this.fetchSurveyPage()
  }

  fetchSurveyPage(pageNumber:number = 0, pageSize:number = 10){
    this.respondantService.viewAllSurvey(pageNumber, pageSize).subscribe({
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
    this.router.navigate(['./respondant-wall/survey', surveyId]);

  }


}
