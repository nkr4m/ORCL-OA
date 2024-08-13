import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class RespondantService {

  baseUrl:any = "http://localhost:8081/"
  baseUrl2:any = "http://localhost:8082/"

  constructor(private http:HttpClient) { }

  viewAllSurvey(pageNumber:number, pageSize:number){
    return this.http.get(`${this.baseUrl}` + `view-all-survey?pageNumber=${pageNumber}&pageSize=${pageSize}`)
  }

  viewSurvey(surveyId:number){
    return this.http.get(`${this.baseUrl}` + `view-survey?surveyId=${surveyId}`)
  }

  saveResponse(data:any, surveyId:any, versionId:any){
    let user = JSON.parse(localStorage.getItem('user'));
    if(user){
      return this.http.post(`${this.baseUrl2}` + `save-response?userId=${user.id}&surveyId=${surveyId}&versionId=${versionId}`, data);
    }
    return this.http.post(`${this.baseUrl2}` + `save-response?surveyId=${surveyId}&versionId=${versionId}`, data);
  }

  fetchSpVersion(surveyId:any, versionId:any){

    return this.http.get(`${this.baseUrl}` + `view-survey-sp-version?surveyId=${surveyId}&versionId=${versionId}`)
  }

  fetchSpVersion2(surveyId:any){
    return this.http.get(`${this.baseUrl}` + `view-survey-sp2-version?surveyId=${surveyId}`)
  }

}
