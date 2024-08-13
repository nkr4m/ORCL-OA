import { NotificationService } from './../core/service/notification.service';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from './../core/service/auth.service';
import { Component } from '@angular/core';

@Component({
  selector: 'app-respondant-landing',
  templateUrl: './respondant-landing.component.html',
  styleUrl: './respondant-landing.component.css'
})
export class RespondantLandingComponent {

  signInForm:FormGroup
  signUpForm:FormGroup
  
  
  showSignIn:boolean = true;

  constructor(private authService:AuthService, private fb:FormBuilder, 
    private router:Router, private notifier:NotificationService){
    this.initSignIn();
    this.initSignUp();
  }

  toggleSignIn(){
    this.showSignIn = !this.showSignIn
  }

  initSignIn(){
    this.signInForm = this.fb.group({
      "email":["", Validators.required],
      "password":["", Validators.required],
      "remember":[""]
    })
  }

  handleSignIn(){
    this.authService.loginUser(this.signInForm.value).subscribe({
      next:(data:any)=>{
        this.notifier.emitNotification('success', "Successfull Login", "Welcome to the console")
        localStorage.clear()
        if(this.signInForm.value.remember == true){
          localStorage.setItem("remember", "Y")
        }
        
        localStorage.setItem("user", JSON.stringify(data))
        this.router.navigate(['respondant-wall']);
        
      },
      error:(error)=>{
        this.notifier.emitNotification('error', 'Invalid Credentials', error.error.errorMessage)
      }
    })
  }

  initSignUp(){
    this.signUpForm = this.fb.group({
      "email":["", Validators.required],
      "password":["", Validators.required],
      "name":["", Validators.required],
      "phoneNumber":["", Validators.required]
    })
  }

  handleSignUp(){
    this.authService.createUser(this.signUpForm.value).subscribe({
      next:(data)=>{
        // console.log(data)
        this.notifier.emitNotification('success', 'Successfull Signup', 'You have successfully registered, Login to access the portal')
        this.showSignIn = !this.showSignIn;
      },
      error:(error)=>{
        // console.log(error)
        this.notifier.emitNotification('error', 'User already exists', error.error.errorMessage)
      }
    })
  }

  navigateRespondantWall(){
    this.router.navigate(['respondant-wall']);
  }

  

}
