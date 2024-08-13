import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  baseUrl = 'http://localhost:8080'

  constructor(private http:HttpClient) { }

  createUser(data:any){
    return this.http.post(`${this.baseUrl}/create-user`, data)
  }

  loginUser(data:any){
    return this.http.post(`${this.baseUrl}/login`, data)
  }

}
