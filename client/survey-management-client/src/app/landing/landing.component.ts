import { Router } from '@angular/router';
import { Component } from '@angular/core';

@Component({
  selector: 'app-landing',
  templateUrl: './landing.component.html',
  styleUrl: './landing.component.css'
})
export class LandingComponent {

  constructor(private router:Router){

  }

  routeCreator(){
    this.router.navigate(['creator-wall']);
  }

  routeRespondant(){
    this.router.navigate(['respondant-landing']);
  }

}
