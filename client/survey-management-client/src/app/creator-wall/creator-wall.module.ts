import { SurveyListLoaderComponent } from './../utility/loaders/survey-list-loader/survey-list-loader.component';
import { MaterialModule } from './../core/module/material/material.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CreatorWallRoutingModule } from './creator-wall-routing.module';
import { CreatorWallComponent } from './creator-wall.component';
import { CreatorDashboardComponent } from './creator-dashboard/creator-dashboard.component';
import { ViewSurveyComponent } from './view-survey/view-survey.component';
import { EditVersionComponent } from './view-survey/edit-version/edit-version.component';
import { AddSectionModalComponent } from './view-survey/add-section-modal/add-section-modal.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AddSurveyModalComponent } from './creator-dashboard/add-survey-modal/add-survey-modal.component';
import { AddVersionModalComponent } from './view-survey/add-version-modal/add-version-modal.component';


@NgModule({
  declarations: [
    CreatorWallComponent,
    CreatorDashboardComponent,
    ViewSurveyComponent,
    EditVersionComponent,
    AddSectionModalComponent,
    AddSurveyModalComponent,
    AddVersionModalComponent
  ],
  imports: [
    CommonModule,
    CreatorWallRoutingModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule
  ]
})
export class CreatorWallModule { }
