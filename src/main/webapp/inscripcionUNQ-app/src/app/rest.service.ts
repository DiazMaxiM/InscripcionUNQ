import { Injectable } from '@angular/core';
import { Estudiante } from './informacion-del-usuario/estudiante.model';
import { MateriaEstudiante } from './materias-aprobadas/materia-estudiante.model';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Usuario } from './autenticacion/usuario.model';
import { Carrera } from './carreras/carrera.model';
import { Materia } from './materias/materia.model';
import { OfertaAcademica } from './oferta-academica/oferta-academica.model';
import { Periodo } from './periodos/periodo.model';
import { Incidencia } from './incidencia-dialogo/incidencia.model';
import { IncidenciaEstado } from './incidencias/incidencia-estado.model';
import { Comision } from './comisiones-de-oferta/comision.model';
import { Equivalencia } from './equivalencias/equivalencia.model';
import { Encuesta } from './encuesta-dialogo/encuesta.model';

const headers = new HttpHeaders({ 'Content-Type': 'application/json' });

@Injectable()
export class RestService {

  constructor(private httpClient: HttpClient) {}

  getEncuestasVigentes(idEstudiante: number) {
    return this.httpClient.get<Array<any>>('/api/poll/user/' + idEstudiante);
  }

  getInformacionEstudiante(dniEstudiante: number, idEncuestaActual: string) {
    return this.httpClient.get('/api/poll/userData/' + dniEstudiante + '/' + idEncuestaActual);
  }

  actualizarInformacionEstudiante(informacionEstudiante: Estudiante) {
    return this.httpClient.post('/api/poll/userData', informacionEstudiante, { headers });
  }

  getMateriasAprobadas(idEstudiante: string) {
    return this.httpClient.get<Array<MateriaEstudiante>>('/api/poll/userApprovedSubjects/' + idEstudiante);
  }

  actualizarMateriasAprobadas(idEstudiante: string, materiasAprobadas) {
    return this.httpClient.post('/api/poll/userApprovedSubjects/' + idEstudiante, materiasAprobadas, { headers });
  }

  obtenerMateriasDisponibles(idEstudiante: string) {
    return this.httpClient.get<Array<MateriaEstudiante>>('/api/poll/userDisapprovedSubjectsWithCommissionAvailable/' + idEstudiante);
  }

  enviarComisionesSeleccionadas(idEstudiante: string, comisionesSeleccionadas) {
    return this.httpClient.post('/api/poll/comisionesSeleccionada/' + idEstudiante, comisionesSeleccionadas, {headers});
  }

  getCertificadoDePreinscripcion(idEstudiante: string) {
    return this.httpClient.get('/api/pdf/' + idEstudiante, { responseType: 'blob'});
  }

  ingresarUsuario(usuario: Usuario) {
    return this.httpClient.post('/api/usuarios/ingresoUsuario', usuario, { headers });
  }

  getCarreras() {
    return this.httpClient.get<Array<Carrera>>('/api/carreras');
  }

  getMaterias() {
    return this.httpClient.get<Array<Materia>>('/api/materias');
  }

  actualizarInformacionMateria(informacionMateria: Materia) {
    return this.httpClient.post('/api/materias/modificarMateria', informacionMateria, { headers });
  }

  agregarNuevaMateria(nuevaMateria: Materia) {
    return this.httpClient.put('/api/materias/nuevaMateria', nuevaMateria, { headers });
  }

  actualizarInformacionCarrera(carreraActualizada: Carrera) {
    return this.httpClient.post('/api/carreras/actualizarCarrera', carreraActualizada, { headers });
  }

  agregarNuevaCarrera(nuevaCarrera: Carrera) {
    return this.httpClient.put('/api/carreras/nuevaCarrera', nuevaCarrera, { headers });
  }

  getOfertas() {
    return this.httpClient.get<Array<OfertaAcademica>>('/api/ofertas-academicas');
  }

  crearNuevaOferta(nuevaOferta: OfertaAcademica) {
    return this.httpClient.put('/api/ofertas-academicas/crearOferta/', nuevaOferta, { headers });
  }

  actualizarInformacionDeOferta(ofertaActualizada: OfertaAcademica) {
    return this.httpClient.post('/api/ofertas-academicas/actualizarOferta/', ofertaActualizada, { headers });
  }

  getMateriasDeCarrera(idCarrera: string) {
    return this.httpClient.get<Array<Materia>>('/api/oferta-academica/materias/' + idCarrera);
  }

  getComisionesDeOferta(idOferta: string) {
    return this.httpClient.get<Array<Comision>>('/api/oferta-academica/comisiones/' + idOferta);
  }

  getPeriodos() {
    return this.httpClient.get<Array<Periodo>>('/api/periodos');
  }

  getTipoPeriodos() {
    return this.httpClient.get<Array<string>>('/api/tipoPeriodos');
  }

  getCantidadPeriodos(idPeriodo: string) {
    return this.httpClient.get<number>('/api/cantidadPeriodos/' + idPeriodo);
  }

  quitarComisionDeOferta(idComision: string, idOferta: string){
    return this.httpClient.delete('api/oferta-academica/eliminarComision/' + idComision + '/' + idOferta);
  }

  crearNuevoPeriodo(nuevoPeriodo: Periodo) {
    return this.httpClient.put('/api/periodos/crearPeriodo/', nuevoPeriodo, { headers });
  }

  getTipoIncidencia() {
    return this.httpClient.get<Array<string>>('/api/tipoIncidencias');
  }

  agregarIncidencia(incidencia: Incidencia){
    return this.httpClient.put('/api/incidencia/', incidencia, {headers});
  }

  getComisionesEnPeriodo(idPeriodo) {
    return this.httpClient.get<Array<Comision>>('/api/comisiones/comisionesEnPeriodo/' + idPeriodo );
  }

  getDias() {
    return this.httpClient.get<Array<string>>('/api/dias');
  }

  crearNuevaComision(comision: Comision) {
    return this.httpClient.put('/api/comision/nuevaComision/', comision, { headers });
  }

  actualizarInformacionDeComision(comision: Comision) {
    return this.httpClient.post('/api/comision/editarComision/', comision, { headers });
  }

  actualizarComisionesDeOferta(idOferta: string, comisionesSeleccionadas) {
    return this.httpClient.post('/api/oferta-academica/actualizar-comisiones/' + idOferta, comisionesSeleccionadas, {headers});
  }

  eliminarComision(idComision: number) {
    return this.httpClient.delete('/api/comision/eliminarComision/' + idComision);
  }

  getEquivalencias(){
    return this.httpClient.get<Array<Equivalencia>>('/api/equivalencias');
  }

  agregarNuevaEquivalencia(equivalencia: Equivalencia) {
    return this.httpClient.put('/api/equivalencia', equivalencia, { headers });
  }

  actualizarEquivalencia(equivalencia: Equivalencia) {
    return this.httpClient.post('/api/equivalencia', equivalencia, { headers });
  }

  getUsuarios(perfil: string) {
    return this.httpClient.get<Array<Usuario>>('/api/usuarios/' + perfil);
  }

  crearNuevoUsuario(usuario: Usuario) {
    return this.httpClient.put('/api/usuarios/nuevoUsuario', usuario, { headers });
  }

  eliminarUsuario(idUsuario: number) {
    return this.httpClient.delete('/api/usuarios/eliminarUsuario/' + idUsuario);
  }

  getIncidencias() {
    return this.httpClient.get<Array<Incidencia>>('/api/incidencias');
  }

  getTipoEstadoIncidencias() {
    return this.httpClient.get<Array<String>>('/api/tipoEstadosIncidencias');
  }
  
  actualizarIncidencia(incidencia: IncidenciaEstado) {
    return this.httpClient.post('/api/actualizar-incidencia/', incidencia, { headers });
  }
  
  actualizarPassword(usuario: Usuario) {
    return this.httpClient.post('/api/usuarios/actualizarPassword', usuario, { headers });
	}
	
	getEncuestas() {
    return this.httpClient.get<Array<any>>('/api/encuestas');
	}
	
	getTipoPerfiles() {
		return this.httpClient.get<Array<String>>('/api/tipoPerfiles');
	}

	actualizarUsuario(usuario: Usuario) {
    return this.httpClient.post('/api/usuarios/actualizarUsuario', usuario, { headers });
	}
	
	actualizarPerfiles(idUsuario: string, perfiles) {
    return this.httpClient.post('/api/usuarios/actualizarPerfiles/' + idUsuario, perfiles, {headers});
	}

	crearEncuesta(encuesta: Encuesta) {
    return this.httpClient.put('/api/encuestas/nuevaEncuesta', encuesta, { headers });
	}
	
	actualizarEncuesta(encuesta: Encuesta) {
    return this.httpClient.post('/api/encuestas/actualizarEncuesta', encuesta, { headers });
	}
	
	getOfertasEnPeriodo(idPeriodo) {
    return this.httpClient.get<Array<OfertaAcademica>>('/api/oferta-academica/ofertasEnPeriodo/' + idPeriodo );
	}
	
	actualizarOfertasDeEncuesta(idEncuesta: number, ofertasSeleccionadas) {
    return this.httpClient.post('/api/encuestas/asociarOfertasParaEncuesta/' + idEncuesta, ofertasSeleccionadas, {headers});
  }

}
