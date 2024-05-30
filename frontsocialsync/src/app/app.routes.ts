import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { ProfileComponent } from './profile/profile.component';
import { SignComponent } from './sign/sign.component';
import { PublicacionComponent } from './publicacion/publicacion.component';
import { AppComponent } from './app.component';
import { RegistrarComponent } from './registrar/registrar.component';
import { CargandoComponent } from './cargando/cargando.component';

export const routes: Routes = [
    {path:'', component:SignComponent},
    {path:'profile/:idusu', component:ProfileComponent},
    {path:'sign', component:SignComponent},
    {path:'publicaciones', component:PublicacionComponent},
    {path:'app', component:AppComponent },
    {path:'home', component:HomeComponent},
    {path:'registrar', component:RegistrarComponent},
    {path:'cargando', component:CargandoComponent}

];
