import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService, User } from '../../../services/auth.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-register',
  imports: [FormsModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {
  name: string = '';
  email: string = '';
  password: string = '';
  role: string = 'USER';
  error: string | null = null;

  constructor(private authService: AuthService, private router: Router) { }

  onRegister(): void {
    this.error = null;
    const newUser = { name: this.name, email: this.email, password: this.password, role: this.role };
    this.authService.register(newUser).subscribe({
      next: (user: User) => this.router.navigate(['/login']),
      error: (err) => this.error = 'Erro ao registrar usuário'
    });
  }

}
