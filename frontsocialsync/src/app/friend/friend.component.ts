import { Component, OnInit, Renderer2 } from '@angular/core';
import { APIHttpService } from '../../core/apihttp.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Usuario } from '../../models/Models';
import { FormControl, ReactiveFormsModule, Validators } from '@angular/forms';
import Swal from 'sweetalert2';
import { CargandoComponent } from '../cargando/cargando.component';

@Component({
  selector: 'app-friend',
  standalone: true,
  imports: [ReactiveFormsModule, CargandoComponent],
  templateUrl: './friend.component.html',
  styleUrl: './friend.component.css'
})
export class FriendComponent implements OnInit {

  httpService: APIHttpService;

  username: string = "";

  mostrarModalCrearPublicacion:boolean = false

  constructor(private http:APIHttpService, private routelink: ActivatedRoute, private router:Router, private renderer: Renderer2){
    this.httpService = http;
  }

  usuarioAPI: Usuario = new Usuario(0,'','','','','','','','','','',null,null,null)

  tituloform = new FormControl('', [Validators.required, Validators.minLength(4),])

  imagenform = new FormControl('')

  verMenu:boolean = false;

  isScrollBlocked = false;

  imagenURL: string | null = null;

  imagenSeleccionadaPublicacion: File  | null = null;

  ngOnInit(): void {
    this.http.actualizarComponente$.subscribe(() => {
      this.init();
    });
    this.init();
  }

  init(){
    this.username = localStorage.getItem('username')??'';

    this.http.getUserForUsename(this.username).subscribe(
      data =>{
        this.usuarioAPI =  data
      }
    )
  }

  sendData(data:boolean){
    this.http.sendBoolean(data);
  }
  
  
  iralperfil(idusu: number){
    this.router.navigate(['/profile', idusu])
  }


  mostrarModal(){
    this.mostrarModalCrearPublicacion = !this.mostrarModalCrearPublicacion
    this.imagenSeleccionadaPublicacion = null
    window.scrollTo({ top: 0, behavior: 'smooth' });
    this.isScrollBlocked = !this.isScrollBlocked;
    if (this.isScrollBlocked) {
      this.renderer.addClass(document.body, 'no-scroll');
    } else {
      this.renderer.removeClass(document.body, 'no-scroll');
    }
  }

  onFileSelected(event: any) {
    this.imagenSeleccionadaPublicacion = event.target.files[0];
    this.imagenURL = this.imagenSeleccionadaPublicacion ? URL.createObjectURL(this.imagenSeleccionadaPublicacion) : null;
    //this.imagesform.setValue(this.imagenSeleccionadaPortada?? null)
  }

  crearPublicacion(){
    this.sendData(true)
    const formData = new FormData();
    formData.append('idusuario', this.usuarioAPI.idUsuario.toString());
    formData.append('file', this.imagenSeleccionadaPublicacion || '');
    formData.append('titulo', this.tituloform.value || '')
    this.http.createPublicacion(formData).subscribe(
      data =>{
        this.sendData(false)
        Swal.fire(data.message)
      }
    )
  }
  cancelarCreacionPublicacion(){

  }
  
}
