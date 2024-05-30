export class Publicacion{
    id: number;
    usuario: Usuario | null;
    titulo: string;
    imagen: string;
    fechaPublicacion: string;
    comentarios: Comentario [] |null;
    megustas: Megusta [];
    tiempoTranscurrido: string
    

    constructor(id:number, usuario:Usuario|null, titulo:string, imagen:string, fechaPublicacion:string, 
                comentarios:Comentario[] |null, megustas:Megusta[], tiempoTranscurrido: string){
        this.id = id;
        this.usuario = usuario;
        this.titulo = titulo;
        this.imagen = imagen;
        this.fechaPublicacion = fechaPublicacion;
        this.comentarios = comentarios;
        this.megustas= megustas;
        this.tiempoTranscurrido = tiempoTranscurrido;

    }
}

export class Usuario{
    idUsuario: number;
    username: string;
    password: string;
    email: string | null;
    nombre: string | null;
    apellido: string |null;
    fechaNacimiento: string | null;
    fechaCreacion: string;
    biografia: string | null;
    fotoPerfil: string;
    fotoPortada: string;
    publicaciones: Publicacion[] | null;
    roles: Rol[] | null;
    amigos: Usuario[] | null;
    

    constructor(idUsuario: number, username: string, password: string, email: string | null, nombre: string | null,
        apellido: string |null, fechaNacimiento: string | null, fechaCreacion: string, biografia: string | null, fotoPerfil: string,
        fotoPortada: string, publicaciones: Publicacion[] | null, roles: Rol[] | null, amigos: Usuario[] | null){

        this.idUsuario = idUsuario;
        this.username = username;
        this.password = password;
        this.email = email;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaCreacion = fechaCreacion;
        this.biografia = biografia;
        this.fotoPerfil = fotoPerfil;
        this.fotoPortada = fotoPortada;
        this.publicaciones = publicaciones;
        this.roles = roles;
        this.amigos = amigos;

    }
}

export class Comentario{
    idComentario: number;
    publicacion: Publicacion;
    usuario:Usuario;
    contenido: string;
    fechaComentario: string;
    tiempoTranscurrido: string;

    constructor(idComentario: number, publicacion: Publicacion, usuario:Usuario, contenido: string, fechaComentario: string,
        tiempoTranscurrido: string){

        this.idComentario = idComentario;
        this.publicacion = publicacion;
        this.usuario = usuario;
        this.contenido = contenido;
        this.fechaComentario = fechaComentario;
        this.tiempoTranscurrido = tiempoTranscurrido;
    }
}

export class Megusta{
    id: number;
    usuario: Usuario;
    publicacion: Publicacion;
    fechaLike: string;

    constructor(id: number, usuario:Usuario, publicacion:Publicacion, fechaLike:string){
        this.id = id;
        this.usuario = usuario;
        this.publicacion = publicacion;
        this.fechaLike = fechaLike;

    }
}

export class Rol{
    id: number;
    name: string;

    constructor(id:number, name:string){
        this.id = id;
        this.name = name;
    }
}

export class solicitudesenviadas{
    id: number;
    usuarioEnviador: Usuario;
    usuarioReceptor: Usuario;
    fechaSolicitudRecibida: string;
    estado: string;

    constructor(id:number,  usuarioEnviador: Usuario, usuarioReceptor: Usuario, fechaSolicitudRecibida: string, estado: string){
        this.id = id;
        this.usuarioReceptor = usuarioReceptor;
        this.usuarioEnviador = usuarioEnviador;
        this.fechaSolicitudRecibida = fechaSolicitudRecibida;
        this.estado = estado;
    }
}

export class solicitudesrecibidas{
    id: number;
    usuarioReceptor: Usuario;
    usuarioEnviador: Usuario;
    fechaSolicitudRecibida: string;
    estado: string;

    constructor(id:number, usuarioReceptor: Usuario, usuarioEnviador: Usuario, fechaSolicitudRecibida: string, estado: string){
        this.id = id;
        this.usuarioReceptor = usuarioReceptor;
        this.usuarioEnviador = usuarioEnviador;
        this.fechaSolicitudRecibida = fechaSolicitudRecibida;
        this.estado = estado;
    }
}
