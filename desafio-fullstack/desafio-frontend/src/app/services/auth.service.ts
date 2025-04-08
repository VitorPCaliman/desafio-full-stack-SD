import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, tap } from "rxjs";

export interface LoginResponse {
    accessToken: string;
    tokenType: string;
  }
  
  export interface User {
    id: number;
    name: string;
    email: string;
    role: string;
  }
  
  @Injectable({
    providedIn: 'root'
  })
  export class AuthService {
  
    private apiAuthUrl = '/auth';
    private apiUserUrl = '/users';
  
    constructor(private http: HttpClient) { }
  
    login(email: string, password: string): Observable<LoginResponse> {
      return this.http.post<LoginResponse>(`${this.apiAuthUrl}/login`, { email, password })
        .pipe(
          tap(response => {

            localStorage.setItem('accessToken', response.accessToken);
          })
        );
    }
  
    register(user: any): Observable<User> {
      return this.http.post<User>(`${this.apiAuthUrl}/register`, user);
    }
  
    getAuthenticatedUser(): Observable<User> {
      return this.http.get<User>(`${this.apiAuthUrl}/context`);
    }
  
    updatePassword(newPassword: string): Observable<any> {
      return this.http.put(`${this.apiUserUrl}/password`, newPassword);
    }
  
    logout(): void {
      localStorage.removeItem('accessToken');
    }
  
    getToken(): string | null {
      return localStorage.getItem('accessToken');
    }
  }