import { EditVersionComponent } from './view-survey/edit-version/edit-version.component';
import { ViewSurveyComponent } from './../creator-wall/view-survey/view-survey.component';
import { CreatorDashboardComponent } from './creator-dashboard/creator-dashboard.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CreatorWallComponent } from './creator-wall.component';

const routes: Routes = [
  { path: '', component: CreatorWallComponent, children:[
    {
      path:'', redirectTo:'creator-dashboard', pathMatch:'full'
    },
    {
      path:'creator-dashboard', component:CreatorDashboardComponent
    },
    {
      path:'survey/:id', component:ViewSurveyComponent
    }
  ] }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CreatorWallRoutingModule { }
