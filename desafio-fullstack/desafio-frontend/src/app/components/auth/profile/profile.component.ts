import { Component, OnInit } from '@angular/core';
import { User, AuthService } from '../../../services/auth.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-profile',
  imports: [FormsModule],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.scss'
})
export class ProfileComponent implements OnInit {
  user: User | null = null;
  newPassword: string = '';
  message: string | null = null;
  error: string | null = null;

  constructor(private authService: AuthService) { }

  ngOnInit(): void {
    this.loadUser();
  }

  loadUser(): void {
    this.authService.getAuthenticatedUser().subscribe({
      next: (userData) => this.user = userData,
      error: () => this.error = 'Erro ao carregar os dados do usuário'
    });
  }

  updatePassword(): void {
    this.authService.updatePassword(this.newPassword).subscribe({
      next: () => this.message = 'Senha atualizada com sucesso',
      error: () => this.error = 'Erro ao atualizar senha'
    });
  }
}
