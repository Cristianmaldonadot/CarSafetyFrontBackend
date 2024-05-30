import { Component, OnInit } from '@angular/core';
import { APIHttpService } from '../../core/apihttp.service';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { CargandoComponent } from '../cargando/cargando.component';

@Component({
  selector: 'app-sign',
  standalone: true,
  imports: [ReactiveFormsModule, RouterLink, CargandoComponent],
  templateUrl: './sign.component.html',
  styleUrl: './sign.component.css'
})
export class SignComponent{

  httpService: APIHttpService;

  constructor(private http:APIHttpService, private router:Router){
    this.httpService = http;
  }
  
  usernameform = new FormControl('')
  passwordform = new FormControl('')

  username = ''

  cargando:boolean = false


  login(){
    this.cargando = true
    let validation: Object = {
      username: this.usernameform.value,
      password: this.passwordform.value
    };
    this.http.login(validation).subscribe(
      data =>{
        localStorage.setItem('token', data.token)
        localStorage.setItem('username', data.Username)
        this.http.getUserForUsename(data.Username).subscribe(
          data =>{
            localStorage.setItem('idUsuario', data.idUsuario)
            this.router.navigate(['/home'])
          }
        )
        
      }
    )

  }

}
