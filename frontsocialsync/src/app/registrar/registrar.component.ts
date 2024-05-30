import { Component } from '@angular/core';
import { APIHttpService } from '../../core/apihttp.service';
import { Router, RouterLink } from '@angular/router';
import { FormControl, ReactiveFormsModule, Validators } from '@angular/forms';
import { CargandoComponent } from '../cargando/cargando.component';

@Component({
  selector: 'app-registrar',
  standalone: true,
  imports: [ReactiveFormsModule, RouterLink, CargandoComponent],
  templateUrl: './registrar.component.html',
  styleUrl: './registrar.component.css'
})
export class RegistrarComponent {

  httpService: APIHttpService;

  constructor(private http:APIHttpService, private router:Router){
    this.httpService = http;
  }
  
  usernameform = new FormControl('', [Validators.required, Validators.minLength(4),])
  passwordform = new FormControl('')
  emailform = new FormControl('', [Validators.required, Validators.email])

  cargando:boolean = false


  registrar(){
    this.cargando = true
    let usuario: Object = {
      username: this.usernameform.value,
      password: this.passwordform.value,
      email: this.emailform.value,
      roles: ['USER']
    };
    this.http.createUsuario(usuario).subscribe(
      data =>{
        this.cargando = false
        this.router.navigate(['/'])
      }
    )

  }


}
