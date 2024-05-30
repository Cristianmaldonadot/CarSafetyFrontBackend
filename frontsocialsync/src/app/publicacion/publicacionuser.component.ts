import { Component, Input, OnInit } from '@angular/core';
import { APIHttpService } from '../../core/apihttp.service';
import { Comentario, Megusta, Publicacion, Usuario } from '../../models/Models';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import Swal from 'sweetalert2';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-publicacionuser',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './publicacion.component.html',
  styleUrl: './publicacion.component.css'
})
export class PublicacionuserComponent implements OnInit {

    httpService: APIHttpService;

  constructor(private http:APIHttpService, private route: ActivatedRoute){
    this.httpService = http;
  }

  usuariosAPI: Usuario[] = []

  username: string = "";

  @Input() usuarioAPI: Usuario = new Usuario(0,'','','','','','','','','','',null,null,null)

  publicacionesAPI: Publicacion[] = []

  comentarioform = new FormControl('');

  tieneLike: boolean = false;

  usuarioBuscado: number = 0;

  idMegusta: number = 0;

  idusu = 0;

  puedesEliminar = true

  idusuparaeliminar = 0;

  mensaje = '';

  siexiste:boolean [] = []

  idsmegustas:number [] = []

  crearArrayBolean(){
    for(let publi of this.publicacionesAPI){
        if(publi.megustas.some(megu => megu.usuario.idUsuario === this.usuarioAPI.idUsuario)){
          this.siexiste.push(true)
        }else{
          this.siexiste.push(false)
        }
    }
  }

  verificaExistencia(){

    const publicacionesAPI = this.publicacionesAPI
    publicacionesAPI.forEach(publicacion => {
      const comentarioEncontrado = publicacion.megustas.find(megus => megus.usuario.idUsuario === this.usuarioBuscado);

      if (comentarioEncontrado) {
        this.tieneLike = true;
        this.idsmegustas.push(comentarioEncontrado.id);
      }else{
        this.tieneLike = false;
        this.idsmegustas.push(0);
      }

    });
  }

  /*ngOnInit(): void {
    this.http.getUsuarios().subscribe(
      data =>{
        this.usuariosAPI = data
        console.log('estos son los comentarios',data[0].comentarios)
      }
    )
  }*/

  ngOnInit(): void {
    this.init()
    
  }

  init(){
    //this.verificaExistencia()
    this.username = localStorage.getItem('username')??'';
    this.idusuparaeliminar = parseInt(localStorage.getItem('idUsuario')??'');

                  this.publicacionesAPI =  this.usuarioAPI.publicaciones??[]
                  this.usuarioBuscado = this.usuarioAPI.idUsuario
                  console.log("estas son las publicaciones", this.publicacionesAPI)
                  this.crearArrayBolean()
                  this.verificaExistencia()
                  console.log("valores boleanos",this.siexiste)
                  console.log("valores ids",this.idsmegustas)
                
       
  }
  initRequest(){
    this.http.getPublicaciones().subscribe(
      data =>{
        this.publicacionesAPI =  data
        this.crearArrayBolean()
        this.verificaExistencia()
        console.log("valores boleanos",this.siexiste)
        console.log("valores ids",this.idsmegustas)
      }
    )
  }


  createComent(publicacion: Publicacion, usuario:Usuario){
    let coment: Object = {
      publicacion: { id: publicacion.id },
      usuario: { idUsuario: usuario.idUsuario},
      contenido: this.comentarioform.value || ''
    };
    this.http.createComent(coment).subscribe(
      data =>{
        console.log(data)
        this.comentarioform.reset()
        this.initRequest()
      }
    )
  }

  createMegusta(publicacion: number, usuario:Usuario){
    let megusta: Object = {
      publicacion: { id: publicacion },
      usuario: { idUsuario: usuario.idUsuario, username: usuario.username}
    };
    this.http.createMegusta(megusta).subscribe(
      data =>{
        this.siexiste = []
        this.idsmegustas = []
        this.initRequest()
      }
    )
  }

  eliminarMegusta(idMegusta: number){
    this.http.deleteMegusta(idMegusta).subscribe(
      data =>{
        this.siexiste = []
        this.idsmegustas = []
        this.initRequest()
      }
    )
  }

  eliminarPublicacion(idpubli:number){
    this.http.deletePublicacion(idpubli).subscribe(
      data =>{
        this.mensaje = data.message
        this.initRequest()
      }
    )
  }
}
