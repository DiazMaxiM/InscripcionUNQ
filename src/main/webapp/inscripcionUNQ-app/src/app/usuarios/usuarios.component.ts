import { Component, OnInit} from '@angular/core';
import { RestService } from '../rest.service';
import {UtilesService} from '../utiles.service';
import { Usuario } from '../autenticacion/usuario.model';
import { MatDialogConfig, MatDialog } from '@angular/material';
import { AppMensajes } from '../app-mensajes.model';
import { AltaUsuarioDialogoComponent } from '../alta-usuario-dialogo/alta-usuario-dialogo.component';
import { FormControl } from '@angular/forms';
import { ActalizacionPerfilesDialogoComponent } from '../actualizacion-perfiles-dialogo/actualizacion-perfiles-dialogo.com\u1E55onent';



@Component({
  selector: 'app-periodo',
  templateUrl: './usuarios.component.html',
  styleUrls: ['../estilo-abm.component.css']
})
export class UsuariosComponent implements OnInit{

	perfiles: String[];

	 usuarios: Usuario[];
	 usuarioBuscado: string;
	 mostrarUsuarios;
	 perfilActual;

    constructor(
      private restService: RestService,
      private utilesService: UtilesService,
      private dialog: MatDialog
    ) {}

		ngOnInit() {
			this.perfilSeleccionado('ADMINISTRADOR');
			this.getPerfiles();
		}

		getPerfiles() {
			this.restService.getTipoPerfiles().subscribe(perfiles => {
				this.perfiles = perfiles;
			},
				(err) => {
					this.utilesService.mostrarMensajeDeError(err);
				});
		}

    crearConfiguracionDialogoParaUsuario(usuario?: Usuario) {
      const dialogConfig = new  MatDialogConfig();
        dialogConfig.disableClose = true;
        dialogConfig.autoFocus = false;
        dialogConfig.width = '600px';
				dialogConfig.height = '400px';
				dialogConfig.data = {
					usuario: usuario
				};
      const dialogRef = this.dialog.open(AltaUsuarioDialogoComponent,
            dialogConfig);
      return dialogRef;
    }

		abrirDialogoParaAltaModificacionDeUsuario(usuario?: Usuario){
      const dialogRef = this.crearConfiguracionDialogoParaUsuario(usuario);
      dialogRef.afterClosed().subscribe(res => {
				this.getUsuariosSegunPerfil();
      });
    }

    getUsuariosSegunPerfil() {
      this.restService.getUsuarios(this.perfilActual).subscribe(usuarios => {
				this.usuarios = usuarios;
				this.mostrarUsuarios = this.usuarios.length > 0;
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
				this.getUsuariosSegunPerfil();
      },
      (err) => {
          this.utilesService.mostrarMensajeDeError(err);
      });
		}
		
		perfilSeleccionado(perfil) {
			this.perfilActual = perfil;
			this.getUsuariosSegunPerfil();
		}

		abrirDialogoParaActualizacionPerfil(usuario){
			const dialogRef = this.crearConfiguracionDialogoParaPerfil(usuario);
      dialogRef.afterClosed().subscribe(res => {
				this.getUsuariosSegunPerfil();
      });
		}

		crearConfiguracionDialogoParaPerfil(usuario: Usuario) {
      const dialogConfig = new  MatDialogConfig();
        dialogConfig.disableClose = true;
        dialogConfig.autoFocus = false;
        dialogConfig.width = '600px';
				dialogConfig.height = '400px';
				dialogConfig.data = {
					usuario: usuario
				};
      const dialogRef = this.dialog.open(ActalizacionPerfilesDialogoComponent,
            dialogConfig);
      return dialogRef;
    }


}
