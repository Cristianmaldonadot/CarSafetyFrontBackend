import { ChangeDetectorRef, Component, ElementRef, OnInit, Renderer2, ViewChild } from '@angular/core';
import { HeaderComponent } from '../header/header.component';
import { PublicacionuserComponent } from '../publicacion/publicacionuser.component';
import { FriendComponent } from '../friend/friend.component';
import { APIHttpService } from '../../core/apihttp.service';
import { Usuario } from '../../models/Models';
import { ActivatedRoute, Router } from '@angular/router';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import html2canvas from 'html2canvas';
import Swal from 'sweetalert2';
import { CargandoComponent } from '../cargando/cargando.component';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [ReactiveFormsModule, HeaderComponent, CargandoComponent, PublicacionuserComponent, FriendComponent, CommonModule],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent implements OnInit {

  @ViewChild('content', {static:false}) content: ElementRef | undefined

  idusu = 1;

  mostrarSubirPortada:boolean = false

  mostrarSubirPerfil:boolean = false

  httpService: APIHttpService;

  constructor(private http:APIHttpService, private routernav:Router, private route: ActivatedRoute, private renderer: Renderer2, private cdr: ChangeDetectorRef){
    this.httpService = http;
  }

  usuarioAPI: Usuario = new Usuario(0,'','','','','','','','','','',null,null,null)

  imagesform = new FormControl()

  imagesformPerfil = new FormControl()

  imagenSeleccionadaPortada: File  | null = null;

  imagenSeleccionadaPerfil: Blob  | null = null;

  imagenURL: string | null = null;

  isScrollBlocked = false;

  cargando:boolean = true

  ngOnInit(): void {
    this.http.actualizarComponente$.subscribe(() => {
      console.log("actualizando component init true")
      this.init();
    });
    this.init();
  }

  init(){
    this.route.params.subscribe(params => {
      this.idusu = params['idusu'];
      this.http.getUserForId(this.idusu).subscribe(
        data =>{
          this.usuarioAPI =  data
          this.cargando = false
        }
      )
    });
  }
  mostrarModalPortada(){
    this.mostrarSubirPortada = !this.mostrarSubirPortada
    window.scrollTo({ top: 0, behavior: 'smooth' });
    this.isScrollBlocked = !this.isScrollBlocked;
    if (this.isScrollBlocked) {
      this.renderer.addClass(document.body, 'no-scroll');
    } else {
      this.renderer.removeClass(document.body, 'no-scroll');
    }
  }

  mostrarModalPerfil(){
    this.mostrarSubirPerfil = !this.mostrarSubirPerfil
    window.scrollTo({ top: 0, behavior: 'smooth' });
    this.isScrollBlocked = !this.isScrollBlocked;
    if (this.isScrollBlocked) {
      this.renderer.addClass(document.body, 'no-scroll');
    } else {
      this.renderer.removeClass(document.body, 'no-scroll');
    }
  }

  onFileSelected(event: any) {
    this.imagenSeleccionadaPortada = event.target.files[0];
    this.imagenURL = this.imagenSeleccionadaPortada ? URL.createObjectURL(this.imagenSeleccionadaPortada) : null;
    //this.imagesform.setValue(this.imagenSeleccionadaPortada?? null)
  }

  onFileSelectedPerfil(event: any) {
    this.imagenSeleccionadaPerfil = event.target.files[0];
    this.imagenURL = this.imagenSeleccionadaPerfil ? URL.createObjectURL(this.imagenSeleccionadaPerfil) : null;
    //this.imagesform.setValue(this.imagenSeleccionadaPerfil?? null)
  }

  guardarImagen(){
    this.cargando = true
    const formData = new FormData();
    formData.append('idusuario', this.usuarioAPI.idUsuario.toString());
    formData.append('file', this.imagenSeleccionadaPortada || '');
    console.log(this.usuarioAPI.fotoPortada);

    this.http.guardarPortada(formData).subscribe(
      data =>{
        this.usuarioAPI = data
        this.mostrarModalPortada();
        this.cargando = false
        //Swal.fire(data.message)
      }
    )
  }
  guardarFotoPerfil(){
    this.cargando = true
    html2canvas(this.content?.nativeElement).then(canvas =>{
      canvas.toBlob(blob =>{
        if(blob){
          const file = new File([blob], 'foto-perfil.jpg', {type: 'image/jpg'});
          const formData = new FormData();
          formData.append('idusuario', this.usuarioAPI.idUsuario.toString());
          formData.append('file', file);
          this.http.guardarPerfil(formData).subscribe(
            data =>{
              this.usuarioAPI = data
              if(data.username){
                //console.log("datausername true", data)
                this.mostrarModalPerfil();
                this.cargando = false;
                //this.cdr.detectChanges();
                //this.actualizarComponente();
              }
              
            }
          )
        }
      })
    })
  }

  isDragging = false;
  startX = 0;
  startY = 0;
  initialX = 0;
  initialY = 0;

  startDragging(event: MouseEvent) {
    this.isDragging = true;
    this.startX = event.clientX - this.initialX;
    this.startY = event.clientY - this.initialY;
    (event.target as HTMLElement).style.cursor = 'grabbing';
  }

  drag(event: MouseEvent) {
    if (this.isDragging) {
      this.initialX = event.clientX - this.startX;
      this.initialY = event.clientY - this.startY;

      const image = event.target as HTMLElement;
      image.style.transform = `translate(${this.initialX}px, ${this.initialY}px)`;
    }
  }

  stopDragging() {
    this.isDragging = false;
    const image = document.querySelector('.image') as HTMLElement;
    if (image) {
      image.style.cursor = 'grab';
    }
  }

  // reload(){
  //   this.cargando = false;
  //   this.mostrarSubirPerfil = false;
  //   this.actualizarComponente()
  // }
   actualizarComponente(): void {
    console.log("actualizando component true")
     this.http.actualizarComponente();
   }
}
