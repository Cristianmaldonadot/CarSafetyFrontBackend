import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { Comentario, Publicacion, Usuario } from '../models/Models';

@Injectable({
  providedIn: 'root'
})
export class APIHttpService {

  private actualizarComponenteSubject = new Subject<void>();

  private terminarCargaSubject = new Subject<boolean>();

  constructor(private http: HttpClient) { }

  actualizarComponente$ = this.actualizarComponenteSubject.asObservable();

  terminarCarga$ = this.terminarCargaSubject.asObservable();

  sendBoolean(terminarCarga:boolean){
    this.terminarCargaSubject.next(terminarCarga);
  }

  actualizarComponente(): void {
    this.actualizarComponenteSubject.next();
  }

  login(validation: Object):Observable<any>{
    return this.http.post('/login', validation)
  }

  createUsuario(usuario: Object):Observable<any>{
    return this.http.post('/registrarusuario', usuario)
  }

  getUserForUsename(username: String):Observable<any>{
    return this.http.get(`/obtenerusuario/${username}`)
  }

  getUserForId(idusu: number):Observable<any>{
    return this.http.get(`/obtenerusuarioid/${idusu}`)
  }

  getUsuarios():Observable<any>{
    return this.http.get('/listarUsuarios')
  }

  getUsuariosFiltrados(idusu:number):Observable<any>{
    return this.http.get(`/listarUsuariosfiltrados/${idusu}`)
  }

  createComent(comentario: Object):Observable<any>{
    return this.http.post('/crearComentario', comentario)
  }

  createMegusta(megusta:Object):Observable<any>{
    return this.http.post('/crearMegusta', megusta)
  }

  deleteMegusta(idMegusta: number):Observable<any>{
    return this.http.delete(`/deleteMegusta/${idMegusta}`)
  }

  getPublicaciones():Observable<any>{
    return this.http.get(`/listarPublicaciones`)
  }

  getPublicacionesPoUser(idusuario: number):Observable<any>{
    return this.http.get(`/listarPublicacionesUsuario/${idusuario}`)
  }

  createFriendship(usuario1:number, usuario2:number, idsolicitud: number):Observable<any>{
    return this.http.post(`/crearAmistadYEliminarSolicitudes/${usuario1}/${usuario2}/${idsolicitud}`, '')
  }

  createSolicitud(usuario1:number, usuario2:number):Observable<any>{
    return this.http.post(`/crearsolicitud/${usuario1}/${usuario2}`, '')
  }

  eliminarSolicitud(usuario1:number, usuario2:number, idsolicitud: number):Observable<any>{
    return this.http.post(`/eliminarSolicitudes/${usuario1}/${usuario2}/${idsolicitud}`, '')
  }
  rechazarSolicitud(usuario1:number, usuario2:number, idsolicitud: number):Observable<any>{
    return this.http.post(`/rechazarSolicitudes/${usuario1}/${usuario2}/${idsolicitud}`, '')
  }

  deletePublicacion(idpubli:number):Observable<any>{
    return this.http.delete(`/eliminarpublicacion/${idpubli}`)
  }
  createPublicacion(data:FormData):Observable<any>{
    return this.http.post('/crearPublicacion', data)
  }

  guardarPortada(data: FormData):Observable<any>{
    return this.http.post('/guardarportada', data)
  }

  guardarPerfil(data: FormData):Observable<any>{
    return this.http.post('/guardarperfil', data)
  }
}
