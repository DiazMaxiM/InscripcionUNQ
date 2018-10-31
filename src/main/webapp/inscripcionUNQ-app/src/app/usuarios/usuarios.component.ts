import { Component, OnInit} from '@angular/core';
import { RestService } from '../rest.service';
import {UtilesService} from '../utiles.service';
import { Usuario } from '../autenticacion/usuario.model';
import { MatDialogConfig, MatDialog } from '@angular/material';
import { AppMensajes } from '../app-mensajes.model';
import { AltaUsuarioDialogoComponent } from '../alta-usuario-dialogo/alta-usuario-dialogo.component';
import { FormControl } from '@angular/forms';



@Component({
  selector: 'app-periodo',
  templateUrl: './usuarios.component.html',
  styleUrls: ['../estilo-abm.component.css']
})
export class UsuariosComponent implements OnInit{

	perfiles: String[];

	 usuarios: Usuario[];
	 usuarioBuscado: string;
    constructor(
      private restService: RestService,
      private utilesService: UtilesService,
      private dialog: MatDialog
    ) {}

		ngOnInit() {
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

    crearConfiguracionDialogoParaUsuario(usuario?) {
      const dialogConfig = new  MatDialogConfig();
        dialogConfig.disableClose = true;
        dialogConfig.autoFocus = false;
        dialogConfig.width = '400px';
        dialogConfig.height = '250px';
      const dialogRef = this.dialog.open(AltaUsuarioDialogoComponent,
            dialogConfig);
      return dialogRef;
    }

    abrirDialogoParaCreacionDeUsuario(){
      const dialogRef = this.crearConfiguracionDialogoParaUsuario();
      dialogRef.afterClosed().subscribe(res => {
      });
    }

    getUsuariosSegunPerfil(perfil) {
      this.restService.getUsuarios(perfil).subscribe(usuarios => {
				this.usuarios = usuarios;
				console.log(usuarios);
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
      },
      (err) => {
          this.utilesService.mostrarMensajeDeError(err);
      });
		}
		
		perfilSeleccionado(perfil) {
			this.getUsuariosSegunPerfil(perfil);
		}


}
