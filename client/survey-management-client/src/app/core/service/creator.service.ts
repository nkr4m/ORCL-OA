import { AnimationQueryMetadata } from '@angular/animations';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CreatorService {

  baseUrl:any = "http://localhost:8081/"

  constructor(private http:HttpClient) { }

  viewAllSurvey(pageNumber:number, pageSize:number){
    return this.http.get(`${this.baseUrl}` + `view-all-survey?pageNumber=${pageNumber}&pageSize=${pageSize}`)
  }

  viewSurvey(surveyId:number){
    return this.http.get(`${this.baseUrl}` + `view-survey?surveyId=${surveyId}`)
  }

  viewAllVersionSection(versionId){
    return this.http.get(`${this.baseUrl}` + `view-version-section?versionId=${versionId}`)
  }

  addSection(data:any, version:any){
    return this.http.post(`${this.baseUrl}` + `create-survey-section?surveyVersionId=${version}`, data)
  }

  addSurvey(data:any){
    return this.http.post(`${this.baseUrl}` + `create-survey`, data)
  }

  addVersion(data:AnimationQueryMetadata, surveyId:any){
    return this.http.post(`${this.baseUrl}` + `create-survey-version?surveyId=${surveyId}`, data)
  }

  modifySections(data:any, versionId:any){
    return this.http.put(`${this.baseUrl}` + `modify-section-order?versionId=${versionId}`, data)
  }

  modifyVersionStatus(versionId:any){
    return this.http.get(`${this.baseUrl}` + `modify-version-status?versionId=${versionId}`);
  }

  fetchUserResults(versionId:any){
    return this.http.get(`${this.baseUrl}` + `results/fetch-results?versionId=${versionId}`);
  }

}
