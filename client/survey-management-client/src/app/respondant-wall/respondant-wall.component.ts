import { Router } from '@angular/router';
import { Component } from '@angular/core';

@Component({
  selector: 'app-respondant-wall',
  templateUrl: './respondant-wall.component.html',
  styleUrl: './respondant-wall.component.css'
})
export class RespondantWallComponent {

  user!:any;


  constructor(private router:Router){
    let user =  localStorage.getItem('user')
    if(user != null){
      this.user = JSON.parse(user)
    }
  }

  onLogin(){
    this.router.navigate(['respondant-landing']);
  }

  onLogout(){
    localStorage.clear()
    this.router.navigate(['/']);
  }

  

}
