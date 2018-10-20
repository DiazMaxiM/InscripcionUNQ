import { Component, OnInit} from '@angular/core';
import { RestService } from '../rest.service';
import {UtilesService} from '../utiles.service';
import { Usuario } from '../autenticacion/usuario.model';
import { MatDialogConfig, MatDialog } from '@angular/material';
import { UsuarioDialogoComponent } from '../usuario-dialogo/usuario-dialogo.component';
import { AppMensajes } from '../app-mensajes.model';


@Component({
  selector: 'app-periodo',
  templateUrl: './usuarios.component.html',
  styleUrls: ['../estilo-abm.component.css']
})
export class UsuariosComponent implements OnInit{

  mostrarUsuarios;

   usuarios: Usuario[];
    constructor(
      private restService: RestService,
      private utilesService: UtilesService,
      private dialog: MatDialog
    ) {}

    ngOnInit() {
      this.usuarios = JSON.parse(localStorage.getItem('usuarios'));
      this.hayUsuariosParaMostrar();
    }

    hayUsuariosParaMostrar() {
      this.mostrarUsuarios = this.usuarios.length > 0;
    }
    crearConfiguracionDialogoParaUsuario(usuario?) {
      const dialogConfig = new  MatDialogConfig();
        dialogConfig.disableClose = true;
        dialogConfig.autoFocus = false;
        dialogConfig.width = '400px';
        dialogConfig.height = '400px';
        dialogConfig.data = {
        usuario: usuario
      };
      const dialogRef = this.dialog.open(UsuarioDialogoComponent,
            dialogConfig);
      return dialogRef;
    }

    abrirDialogoParaCreacionDeUsuario(){
      const dialogRef = this.crearConfiguracionDialogoParaUsuario();
      dialogRef.afterClosed().subscribe(res => {
        console.log(res);
        if (AppMensajes.OK == res) {
          this.utilesService.mostrarMensaje(AppMensajes.CREACION_USUARIO_EXITOSO);
          this.getUsuarios();
        }
      });
    }

    getUsuarios() {
      this.restService.getUsuarios().subscribe(usuarios => {
        this.usuarios = usuarios;
        this.hayUsuariosParaMostrar();
      },
      (err) => {
          this.utilesService.mostrarMensajeDeError(err);
      });
    }


    eliminarUsuario(usuario){
      const mensaje = '¿Está seguro de que desea eliminar el usuario seleccionada?';
      this.utilesService.mostrarDialogoConfirmacion(mensaje).subscribe(confirma => {
        if (confirma) {
        this.eliminar(usuario);
        }
    });
  }

    eliminar(usuario) {
      this.restService.eliminarUsuario(usuario.id).subscribe(res => {
        this.utilesService.mostrarMensaje(AppMensajes.ELIMINACION_USUARIO_EXITOSO);
        this.getUsuarios();
      },
      (err) => {
          this.utilesService.mostrarMensajeDeError(err);
      });
    }


}
