import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { MaterialModule } from './../core/module/material/material.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { RespondantWallRoutingModule } from './respondant-wall-routing.module';
import { RespondantWallComponent } from './respondant-wall.component';
import { ViewAllSurveyComponent } from './view-all-survey/view-all-survey.component';
import { ViewSurveyComponent } from './view-survey/view-survey.component';
import { ViewVersionComponent } from './view-survey/view-version/view-version.component';


@NgModule({
  declarations: [
    RespondantWallComponent,
    ViewAllSurveyComponent,
    ViewSurveyComponent,
    ViewVersionComponent,
  ],
  imports: [
    CommonModule,
    RespondantWallRoutingModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule
  ]
})
export class RespondantWallModule { }
