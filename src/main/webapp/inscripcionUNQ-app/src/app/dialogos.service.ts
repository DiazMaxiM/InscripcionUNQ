import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { MatDialogRef, MatDialog, MatDialogConfig } from '@angular/material';
import { TipoIncidenciaDialogoComponent } from './tipo-incidencia-dialogo/tipo-incidencia-dialogo.component';
import { TipoIncidencia } from './tipo-incidencia-dialogo/tipo-incidencia.model';
import { EncuestaDialogoComponent } from './encuesta-dialogo/encuesta-dialogo.component';
import { OfertasDeEncuestaDialogoComponent } from './ofertas-de-encuesta-dialogo/ofertas-de-encuesta-dialogo.component';
import { Encuesta } from './encuesta-dialogo/encuesta.model';
import { ReporteDialogoComponent } from './reporte-dialogo/reporte-dialogo.component';
import { Carrera } from './carreras/carrera.model';
import { CarreraDialogoComponent } from './carrera-dialogo/carrera-dialogo.component';
import { Materia } from './materias/materia.model';
import { ModificacionDeMateriaDialogoComponent } from './modificacion-de-materia-dialogo/modificacion-de-materia-dialogo.component';
import { EquivalenciaDialogoComponent } from './equivalencia-dialogo/equivalencia-dialogo.component';
import { Equivalencia } from './equivalencias/equivalencia.model';
import { OfertaAcademica } from './oferta-academica/oferta-academica.model';
import { OfertaAcademicaDialogoComponent } from './oferta-academica-dialogo/oferta-academica.dialogo.component';
import { SeleccionDePeriodoDialogoComponent } from './seleccion-de-periodo-dialogo/seleccion-de-periodo-dialogo.component';
import { ComisionesDeOfertaDialogoComponent } from './comisiones-de-oferta-dialogo/comisiones-de-oferta-dialogo.component';
import { PeriodoDialogoComponent } from './periodo-dialogo/periodo-dialogo.component';
import { Periodo } from './periodos/periodo.model';
import { Comision } from './comisiones-de-oferta/comision.model';
import { ComisionDialogoComponent } from './comision-dialogo/comision-dialogo.component';
import { Usuario } from './autenticacion/usuario.model';
import { AltaUsuarioDialogoComponent } from './alta-usuario-dialogo/alta-usuario-dialogo.component';
import { ActalizacionPerfilesDialogoComponent } from './actualizacion-perfiles-dialogo/actualizacion-perfiles-dialogo.com\u1E55onent';
import { IncidenciaDialogoComponent } from './incidencia-dialogo/incidencia-dialogo.component';
import { EdicionUsuarioDialogoComponent } from './edicion-usuario-dialogo/edicion-usuario-dialogo.component';
import { IncidenciaEstado } from './incidencias/incidencia-estado.model';
import { ModificacionDeIncidenciaDialogoComponent } from './modificacion-de-incidencia-dialogo/modificacion-de-incidencia-dialogo.component';
import { SeleccionDeComisionDialogoComponent } from './seleccion-de-comision-dialogo/seleccion-de-comision-dialogo.component';

@Injectable()
export class DialogosService {
		
	constructor(private dialog: MatDialog) { }

  abrirDialogoTipoDeIncidencia(tipoIncidencia?: TipoIncidencia): Observable<any> {
        let dialogRef: MatDialogRef<TipoIncidenciaDialogoComponent>;
				dialogRef = this.dialog.open(TipoIncidenciaDialogoComponent, this.configuracionDialogo());
				dialogRef.componentInstance.tipoDeIncidencia = tipoIncidencia;
        return dialogRef.afterClosed();
		}
		
	abrirDialogoEncuesta(encuesta?): Observable<any> {
			let dialogRef: MatDialogRef<EncuestaDialogoComponent>;
			dialogRef = this.dialog.open(EncuestaDialogoComponent);
			dialogRef.componentInstance.encuestaSeleccionada = encuesta;
			
			return dialogRef.afterClosed();
	}

	abrirDialogoOfertaAcademicaDeEncuesta(encuesta?: Encuesta): Observable<any> {
		let dialogRef: MatDialogRef<OfertasDeEncuestaDialogoComponent>;
		dialogRef = this.dialog.open(OfertasDeEncuestaDialogoComponent, this.configuracionDialogo());
		dialogRef.componentInstance.encuesta = encuesta;
		dialogRef.componentInstance.ofertasSeleccionados = encuesta.ofertasAcademicas;
		return dialogRef.afterClosed();
}

abrirDialogoReportes(encuesta?: Encuesta): Observable<any> {
	let dialogRef: MatDialogRef<ReporteDialogoComponent>;
	dialogRef = this.dialog.open(ReporteDialogoComponent, this.configuracionDialogo());
	dialogRef.componentInstance.encuesta = encuesta;
	return dialogRef.afterClosed();
}

abrirDialogoCarrera(carrera?: Carrera): Observable<any> {
	let dialogRef: MatDialogRef<CarreraDialogoComponent>;
	dialogRef = this.dialog.open(CarreraDialogoComponent, this.configuracionDialogo());
	dialogRef.componentInstance.carrera = carrera;
	return dialogRef.afterClosed();
}

abrirDialogoMateria(materia?: Materia): Observable<any> {
	let dialogRef: MatDialogRef<ModificacionDeMateriaDialogoComponent>;
	dialogRef = this.dialog.open(ModificacionDeMateriaDialogoComponent, this.configuracionDialogo());
	dialogRef.componentInstance.materia = materia;
	return dialogRef.afterClosed();
}

abrirDialogoEquivalencia(equivalencia?: Equivalencia): Observable<any> {
	let dialogRef: MatDialogRef<EquivalenciaDialogoComponent>;
	dialogRef = this.dialog.open(EquivalenciaDialogoComponent, this.configuracionDialogo());
	dialogRef.componentInstance.equivalencia = equivalencia;
	return dialogRef.afterClosed();
}

abrirDialogoOfertaAcademica(oferta?: OfertaAcademica): Observable<any> {
	let dialogRef: MatDialogRef<OfertaAcademicaDialogoComponent>;
	dialogRef = this.dialog.open(OfertaAcademicaDialogoComponent, this.configuracionDialogo());
	dialogRef.componentInstance.ofertaSeleccionada = oferta;
	return dialogRef.afterClosed();
}

abrirDialogoSeleccionDePeriodo(): Observable<any> {
	let dialogRef: MatDialogRef<SeleccionDePeriodoDialogoComponent>;
	dialogRef = this.dialog.open(SeleccionDePeriodoDialogoComponent, this.configuracionDialogo());
	return dialogRef.afterClosed();
}

abrirDialogoComisionesDeOferta(): Observable<any> {
	let dialogRef: MatDialogRef<ComisionesDeOfertaDialogoComponent>;
	dialogRef = this.dialog.open(ComisionesDeOfertaDialogoComponent, this.configuracionDialogo());
	return dialogRef.afterClosed();
}

abrirDialogoPeriodo(periodo?: Periodo): Observable<any> {
	let dialogRef: MatDialogRef<PeriodoDialogoComponent>;
	dialogRef = this.dialog.open(PeriodoDialogoComponent, this.configuracionDialogo());
	return dialogRef.afterClosed();
}

abrirDialogoComision(comision?: Comision): Observable<any> {
	let dialogRef: MatDialogRef<ComisionDialogoComponent>;
	dialogRef = this.dialog.open(ComisionDialogoComponent, this.configuracionDialogo());
	dialogRef.componentInstance.comisionSeleccionada = comision;
	return dialogRef.afterClosed();
}

abrirDialogoUsuario(usuario?: Usuario): Observable<any> {
	let dialogRef: MatDialogRef<AltaUsuarioDialogoComponent>;
	dialogRef = this.dialog.open(AltaUsuarioDialogoComponent, this.configuracionDialogo());
	dialogRef.componentInstance.usuario = usuario;
	return dialogRef.afterClosed();
}

abrirDialogoPerfil(usuario?: Usuario): Observable<any> {
	let dialogRef: MatDialogRef<ActalizacionPerfilesDialogoComponent>;
	dialogRef = this.dialog.open(ActalizacionPerfilesDialogoComponent, this.configuracionDialogo());
	dialogRef.componentInstance.usuario = usuario;
	return dialogRef.afterClosed();
}

abrirDialogoReporteIncidencia(): Observable<any> {
	let dialogRef: MatDialogRef<IncidenciaDialogoComponent>;
	dialogRef = this.dialog.open(IncidenciaDialogoComponent, this.configuracionDialogo());
	return dialogRef.afterClosed();
}


abrirDialogoModificacionPassword(): Observable<any> {
	let dialogRef: MatDialogRef<EdicionUsuarioDialogoComponent>;
	dialogRef = this.dialog.open(EdicionUsuarioDialogoComponent, this.configuracionDialogo());
	return dialogRef.afterClosed();
}

abrirDialogoEdicionDeIncidencia(incidencia: IncidenciaEstado): Observable<any> {
	let dialogRef: MatDialogRef<ModificacionDeIncidenciaDialogoComponent>;
	dialogRef = this.dialog.open(ModificacionDeIncidenciaDialogoComponent, this.configuracionDialogo());
	dialogRef.componentInstance.incidencia = incidencia;
	return dialogRef.afterClosed();
}

abrirDialogoParaSeleccionarComision(materia: Materia): Observable<any> {
	let dialogRef: MatDialogRef< SeleccionDeComisionDialogoComponent>;
	dialogRef = this.dialog.open(SeleccionDeComisionDialogoComponent, this.configuracionDialogo());
	dialogRef.componentInstance.materia = materia;
	return dialogRef.afterClosed();
}

configuracionDialogo() {
		const dialogConfig = new MatDialogConfig();
		dialogConfig.disableClose = true;
		dialogConfig.autoFocus = false;
		dialogConfig.width = '600px';
		dialogConfig.height = '400px';
		return dialogConfig;
}

}
