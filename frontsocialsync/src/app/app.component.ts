import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { SignComponent } from './sign/sign.component';
import { HeaderComponent } from './header/header.component';
import { ProfileComponent } from './profile/profile.component';
import { HomeComponent } from './home/home.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, SignComponent, HeaderComponent, ProfileComponent, HomeComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = "hola"

  header = true
}
