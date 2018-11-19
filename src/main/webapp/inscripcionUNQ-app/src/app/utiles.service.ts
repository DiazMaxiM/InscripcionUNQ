import { Injectable, NgZone } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material';
import { FeedbackUsuarioDialogoComponent } from './feedback-usuario-dialogo/feedback-usuario-dialogo.component';
import { Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { Materia } from './materias/materia.model';
import { Periodo } from './periodos/periodo.model';
import { AppMensajes } from './app-mensajes.model';
import { FormGroup, FormControl } from '@angular/forms';
import { Carrera } from './carreras/carrera.model';
import { BootController } from './boot-control';

@Injectable()
export class UtilesService {
	dialogo;

	constructor(
		private dialog: MatDialog,
		private router: Router,
		private ngZone: NgZone,
	) {
	}

	mostrarMensajeYRedireccionar(mensaje, ruta) {
		const dialogConfig = this.crearConfiguracionDelDialogo(mensaje);
		const dialogRef = this.dialog.open(FeedbackUsuarioDialogoComponent,
			dialogConfig);

		dialogRef.afterClosed().subscribe(result => {
			this.router.navigate([ruta]);
		});
	}

	crearConfiguracionDelDialogo(msj: String, cargando = false, confirmar = false) {
		const dialogConfig = new MatDialogConfig();
		dialogConfig.disableClose = true;
		dialogConfig.autoFocus = false;
		dialogConfig.width = '600px';
		dialogConfig.height = '200px';
		dialogConfig.data = {
			mensaje: msj,
			cargando: cargando,
			confirmar: confirmar
		};
		return dialogConfig;
	}

	irA(ruta) {
		this.router.navigate([ruta]);
	}

	mostrarMensaje(mensaje) {
		const dialogConfig = this.crearConfiguracionDelDialogo(mensaje);
		this.dialog.open(FeedbackUsuarioDialogoComponent,
			dialogConfig);
	}

	salir() {
		localStorage.clear();
		// Triggers the reboot in main.ts
		this.ngZone.runOutsideAngular(() => BootController.getbootControl().restart());
		this.irA('');
	}

	crearFecha(fecha) {
		const date = new Date();
		date.setDate(fecha.dayOfMonth);
		date.setMonth(fecha.monthValue - 1);
		date.setFullYear(fecha.year);
		date.setHours(fecha.hora);
		date.setMinutes(fecha.minutos);
		date.setSeconds(fecha.second);
		return date;
	}

	mostrarMensajeYSalir(mensaje) {
		this.mostrarMensaje(mensaje);
		this.salir();
	}

	mostrarMensajeDeError(error: HttpErrorResponse) {
		if (error.status >= 500) {
			const mensaje = 'En este momento no se puede procesar la petición. Por favor, vuelva a intentar más tarde';
			this.mostrarMensajeYSalir(mensaje);
		} else {
			this.mostrarMensaje(error.error.msg);
		}
	}

	activarDialogoCargando() {
		const mensaje = 'Procesando...';
		const dialogConfig = this.crearConfiguracionDelDialogo(mensaje, true);
		const dialogRef = this.dialog.open(FeedbackUsuarioDialogoComponent,
			dialogConfig);
		this.dialogo = dialogRef;
	}

	desactivarDialogoCargandoYRedireccionar(direccion: string) {
		this.dialogo.close();
		this.dialogo = null;
		this.irA(direccion);
	}

	desactivarDialogoCargando() {
		this.dialogo.close();
		this.dialogo = null;
	}

	mostrarDialogoConfirmacion(mensaje) {
		const dialogConfig = this.crearConfiguracionDelDialogo(mensaje, false, true);
		const dialogRef = this.dialog.open(FeedbackUsuarioDialogoComponent,
			dialogConfig);
		return dialogRef.afterClosed().pipe(
			map((confirmar) => {
				return confirmar;
			})
		);
	}

	nuevoHorario(horario) {
		const date = new Date();
		date.setHours(horario.hora);
		date.setMinutes(horario.minutos);
		date.setSeconds(horario.second);
		return date;
	}

	ordenarComisionesPorNombre(comisiones) {
		const comisionesOrdenadas = comisiones.sort(function (o1, o2) {
			if (o1.nombre > o2.nombre) {
				return 1;
			} else if (o1.nombre < o2.nombre) {
				return -1;
			}
			return 0;
		});
		return comisionesOrdenadas;
	}

	validateAllFormFields(formGroup: FormGroup) {
		Object.keys(formGroup.controls).forEach(field => {
			const control = formGroup.get(field);
			if (control instanceof FormControl) {
				control.markAsTouched({ onlySelf: true });
			} else if (control instanceof FormGroup) {
				this.validateAllFormFields(control);
			}
		});
	}

	materiaValida(materias: Materia[], nombreMateria) {
		const materia = this.obtenerMateria(materias, nombreMateria);
		if (materia == null) {
			this.mostrarMensaje(AppMensajes.NO_HAY_MATERIA_SELECCIONADA);
			return false;
		} else {
			return true;
		}
	}

	obtenerMateria(materias: Materia[], nombreMateria) {
		return materias.find(materia => materia.nombre == nombreMateria);
	}

	periodoValido(periodos: Periodo[], codigoPeriodo) {
		const periodo = this.obtenerPeriodo(periodos, codigoPeriodo);
		if (periodo == null) {
			this.mostrarMensaje(AppMensajes.NO_HAY_PERIODO_SELECCIONADO);
			return false;
		} else {
			return true;
		}
	}

	obtenerPeriodo(periodos: Periodo[], codigoPeriodo) {
		return periodos.find(periodo => periodo.codigo == codigoPeriodo);
	}

	carreraValida(carreras: Carrera[], descripcionCarrera) {
		const carrera = this.obtenerCarrera(carreras, descripcionCarrera);
		if (carrera == null) {
			this.mostrarMensaje(AppMensajes.NO_HAY_CARRERA_SELECCIONADA);
			return false;
		} else {
			return true;
		}
	}

	obtenerCarrera(carreras: Carrera[], descripcionCarrera) {
		return carreras.find(carrera => carrera.descripcion == descripcionCarrera);
	}

	soloNumero(event): boolean {
		const charCode = (event.which) ? event.which : event.keyCode;
		if (charCode > 31 && (charCode < 48 || charCode > 57)) {
			return false;
		}
		return true;
	}

	ordenarString(lista) {
		const listaOrdenada = lista.sort(function (a, b) {
			return (a).localeCompare(b);
	});
		return listaOrdenada;
	}

	armarHorario(horario) {
		return {
			'hour': horario.hora,
			'minute': horario.minutos
		}
	}

	armarHorarioParaServidor(horario) {
		return {
			'hora': horario.hour,
			'minutos': horario.minute
		}
	}
}