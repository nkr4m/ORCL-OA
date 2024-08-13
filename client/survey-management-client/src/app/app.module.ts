import { ErrorInterceptorService } from './core/interceptor/error-interceptor.service';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { MaterialModule } from './core/module/material/material.module';
import { RespondantService } from './core/service/respondant.service';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { LandingComponent } from './landing/landing.component';
import { RespondantLandingComponent } from './respondant-landing/respondant-landing.component';
import { ToastModule} from 'primeng/toast'
import { MessageService} from 'primeng/api';
import { FooterComponent } from './core/common/footer/footer.component';
import { SurveyListLoaderComponent } from './utility/loaders/survey-list-loader/survey-list-loader.component'
@NgModule({
  declarations: [
    AppComponent,
    LandingComponent,
    RespondantLandingComponent,
    FooterComponent,
    SurveyListLoaderComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    ToastModule,

  ],
  providers: [
    RespondantService,
    provideAnimationsAsync(),
    {
      provide:HTTP_INTERCEPTORS, useClass: ErrorInterceptorService, multi: true
    },
    MessageService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
