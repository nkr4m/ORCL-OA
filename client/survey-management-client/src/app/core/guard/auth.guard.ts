import { ActivatedRouteSnapshot, CanActivate, CanActivateFn, Router, RouterStateSnapshot } from '@angular/router';
import { Injectable } from '@angular/core';
@Injectable({
  providedIn: 'root'
})
export class AuthActivateRouteGuard implements CanActivate {
    
    constructor(private router: Router){

    }

    canActivate(route:ActivatedRouteSnapshot, state:RouterStateSnapshot){

      let user = localStorage.getItem('user')
      if(user == null || user == undefined){
      }else{
        this.router.navigate(['respondant-wall']);
      }

      if(user){
        return false;
      }else{
        return true;
      }
    }

}