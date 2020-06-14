import { Component, OnInit} from '@angular/core';
import { UtilesService } from '../utiles.service';
import { RestService } from '../rest.service';
import { MatDialogRef } from '@angular/material/dialog';
import { Usuario } from '../autenticacion/usuario.model';
import { AppMensajes } from '../app-mensajes.model';

@Component({
	selector: 'app-actualizacion-perfiles-dialogo',
	templateUrl: './actualizacion-perfiles-dialogo.component.html',
	styleUrls: ['../dialogo-abm.component.css']
})

export class ActalizacionPerfilesDialogoComponent implements OnInit {
	perfiles: String[];
	perfilesSeleccionados: String[];
	usuario: Usuario;

	constructor(
		private restService: RestService,
		private utilesService: UtilesService,
		private dialogRef: MatDialogRef<ActalizacionPerfilesDialogoComponent>) {
	}

	ngOnInit() {
		this.getPerfiles();
		this.perfilesSeleccionados = this.usuario.perfiles;
	}

	getPerfiles() {
		this.restService.getTipoPerfiles().subscribe(perfiles => {
			this.perfiles = this.utilesService.ordenarString(perfiles);
		},
			(err) => {
				this.utilesService.mostrarMensajeDeError(err);
			});
	}

	onChange(perfil, $event) {
		if ($event.checked) {
			this.perfilesSeleccionados.push(perfil);
		} else {
			this.perfilesSeleccionados.forEach(perfilSeleccionado => {
				if (perfilSeleccionado == perfil) {
					this.perfilesSeleccionados.splice(this.perfilesSeleccionados.indexOf(perfilSeleccionado), 1);
				}
			});
		}
	}

	verificarSeleccion(perfil) {
		let perfilChecked = false;
		this.perfilesSeleccionados.forEach(perfilSeleccionado => {
			if (perfil == perfilSeleccionado) {
				perfilChecked = true;
			}
		});
		return perfilChecked;
	}

	guardar() {
		if (this.perfilesSeleccionados.length == 0) {
			this.utilesService.mostrarMensaje('Debe seleccionar un perfil')
		} else {
			this.actualizarPerfiles();
		}
	}

	actualizarPerfiles() {
		this.restService.actualizarPerfiles(this.usuario.id, this.perfilesSeleccionados).subscribe(res => {
			this.utilesService.mostrarMensaje(AppMensajes.MODIFICACION_PERFIL_EXITOSO);
			this.cerrar();
		},
			(err) => {
				this.utilesService.mostrarMensajeDeError(err);
			});
	}

	cerrar() {
		this.dialogRef.close();
	}
}