import { Component } from '@angular/core';
import { APIHttpService } from '../../core/apihttp.service';
import { Router } from '@angular/router';
import { Usuario, solicitudesenviadas, solicitudesrecibidas } from '../../models/Models';
import { SweetAlert2Module } from '@sweetalert2/ngx-sweetalert2';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-menuaction',
  standalone: true,
  imports: [SweetAlert2Module],
  templateUrl: './menuaction.component.html',
  styleUrl: './menuaction.component.css'
})
export class MenuactionComponent {

  httpService: APIHttpService;

  username: string = "";

  constructor(private http:APIHttpService, private router:Router){
    this.httpService = http;
  }

  usuariosAPI: Usuario[] = []

  solicitudEnviadasAPI: solicitudesenviadas[] = []
  solicitudRecibidaAPI: solicitudesrecibidas[] = []

  mensaje:string = "";

  idusu:number = 0;

  ngOnInit(): void {

    this.idusu = parseInt(localStorage.getItem('idUsuario') || '');

    if (!isNaN(this.idusu)) {
      this.iniciar();
    } else {
      console.error('Error: idusu no se ha asignado correctamente');
    }

  }

  iniciar(){
    
    this.http.getUsuariosFiltrados(this.idusu).subscribe(
      data =>{
        this.usuariosAPI =  data
        console.log("usuarios filtrados", this.usuariosAPI)
      }
    )
    const username = localStorage.getItem("username")
    this.http.getUserForUsename(username??'').subscribe(
      data =>{
        this.solicitudRecibidaAPI = data.solicitudesrecibidas
        this.solicitudEnviadasAPI = data.solicitudesenviadas
      }
    )
  }

  

  enviarSolicitud(idUsuarioAmigo: number){0
    this.sendData(true)
    const idusuario = localStorage.getItem("idUsuario")
    const idusuarioint = parseInt(idusuario?? "0")

    this.http.createSolicitud(idusuarioint, idUsuarioAmigo).subscribe(
      data =>{
        this.iniciar();
        Swal.fire(data.message)
        this.sendData(false)
      }
    )
  }

  aceptarSolicitud(idUsuarioAmigo: number, idSolicitud: number){
    this.sendData(true)
    const idusuario = localStorage.getItem("idUsuario")
    const idusuarioint = parseInt(idusuario?? "0")

    this.http.createFriendship(idUsuarioAmigo, idusuarioint, idSolicitud).subscribe(
      data =>{
        Swal.fire(data.message)
        this.iniciar();
        this.actualizarComponente();
        this.sendData(false)
      }
    )
  }

  cancelarSolicitud(idusuarioint: number, idUsuarioAmigo: number, idSolicitud: number){
    this.sendData(true)
    this.http.eliminarSolicitud(idusuarioint, idUsuarioAmigo, idSolicitud).subscribe(
      data =>{
        Swal.fire(data.message)
        this.iniciar();
        this.sendData(false)
      }
    )
  }

  rechazarSolicitud(idusuarioint: number, idUsuarioAmigo: number, idSolicitud: number){

  }

  actualizarComponente(): void {
    this.http.actualizarComponente();
  }

  iralperfil(idusu: number){
    this.router.navigate(['/profile', idusu])
  }

  sendData(data:boolean){
    this.http.sendBoolean(data);
  }

}