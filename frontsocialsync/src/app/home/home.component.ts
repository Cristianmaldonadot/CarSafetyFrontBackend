import { Component, OnInit } from '@angular/core';
import { HeaderComponent } from '../header/header.component';
import { PublicacionComponent } from '../publicacion/publicacion.component';
import { FriendComponent } from '../friend/friend.component';
import { MenuactionComponent } from '../menuaction/menuaction.component';
import { CargandoComponent } from '../cargando/cargando.component';
import { APIHttpService } from '../../core/apihttp.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [HeaderComponent, PublicacionComponent, FriendComponent, MenuactionComponent, CargandoComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit {

  cargando:boolean = true

  httpService: APIHttpService;

  constructor(private http:APIHttpService){
    this.httpService = http;
  }
  ngOnInit(): void {
    this.http.terminarCarga$.subscribe(data => {
      this.cargando = data;
    });
  }


  
  

}
