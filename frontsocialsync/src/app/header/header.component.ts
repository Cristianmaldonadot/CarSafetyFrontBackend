import { Component, Input, OnInit } from "@angular/core";
import { Router, RouterLink, RouterOutlet } from "@angular/router";
import { PublicacionComponent } from "../publicacion/publicacion.component";
import { SignComponent } from "../sign/sign.component";
import { APIHttpService } from "../../core/apihttp.service";
import { Usuario } from "../../models/Models";


@Component({
  selector: 'app-header',
  standalone: true,
  imports: [RouterOutlet, PublicacionComponent, SignComponent, RouterLink],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent implements OnInit {

  httpService: APIHttpService;

  username: string = "";

  idusu = 0;

  constructor(private http:APIHttpService, private router:Router){
    this.httpService = http;
  }

  @Input() usuarioAPI: Usuario = new Usuario(0,'','','','','','','','','','',null,null,null)

  verMenu:boolean = false;

  ngOnInit(): void {
    const token = localStorage.getItem('token')
    if(token === null){
      this.router.navigate(['/'])
    }else{
      this.username = localStorage.getItem('username')??'';

    this.http.getUserForUsename(this.username).subscribe(
      data =>{
        this.usuarioAPI =  data
        this.idusu = data.idUsuario
      }
    )

    }

    
  }
  verMenuPerfil(){
    this.verMenu = !this.verMenu;
  }

  cerrarSesion(){
    localStorage.removeItem('token');
    this.router.navigate(['/'])
    localStorage.clear();
    sessionStorage.clear();
  }

  verProfile(){
    this.router.navigate(['/profile', this.idusu])
  }

  verHome(){
    this.router.navigate(['home'])
  }

}




