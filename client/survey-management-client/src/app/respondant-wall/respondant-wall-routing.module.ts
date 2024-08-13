import { ViewSurveyComponent } from './view-survey/view-survey.component';
import { ViewAllSurveyComponent } from './view-all-survey/view-all-survey.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RespondantWallComponent } from './respondant-wall.component';

const routes: Routes = [
  { path: '', component: RespondantWallComponent, children:[
    {
      path:'', redirectTo:'view-all-survey', pathMatch:'full'
    },
    {
      path:'view-all-survey', component:ViewAllSurveyComponent
    },
    {
      path:'survey/:id', component:ViewSurveyComponent
    }
  ]}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class RespondantWallRoutingModule { }
