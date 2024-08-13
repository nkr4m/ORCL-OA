import { AuthActivateRouteGuard } from './core/guard/auth.guard';
import { RespondantLandingComponent } from './respondant-landing/respondant-landing.component';
import { LandingComponent } from './landing/landing.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path:'', component:LandingComponent
  },
  {
    path:'respondant-landing', component:RespondantLandingComponent, canActivate:[AuthActivateRouteGuard]
  },
  { path: 'creator-wall', loadChildren: () => import('./creator-wall/creator-wall.module').then(m => m.CreatorWallModule) }, 
  { path: 'respondant-wall', loadChildren: () => import('./respondant-wall/respondant-wall.module').then(m => m.RespondantWallModule) }];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
