import { Injectable } from '@angular/core';
import { Estudiante } from './informacion-del-usuario/estudiante.model';
import { MateriaEstudiante } from './materias-aprobadas/materia-estudiante.model';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Usuario } from './autenticacion/usuario.model';
import { Carrera } from './carreras/carrera.model';
import { Materia } from './materias/materia.model';
import { OfertaAcademica } from './oferta-academica/oferta-academica.model';
import { Comision } from './comisiones-de-oferta/comision.model';
import { Periodo } from './periodos/periodo.model';

@Injectable()
export class RestService {

  constructor(private httpClient: HttpClient) {}

  getEncuestasVigentes(idEstudiante: number) {
    return this.httpClient.get('/api/poll/user/' + idEstudiante);
  }

  getInformacionEstudiante(dniEstudiante: string, idEncuestaActual: string) {
    return this.httpClient.get('/api/poll/userData/' + dniEstudiante + '/' + idEncuestaActual);
  }

  actualizarInformacionEstudiante(informacionEstudiante: Estudiante) {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.httpClient.post('/api/poll/userData', informacionEstudiante, { headers });
  }

  getMateriasAprobadas(idEstudiante: string) {
    return this.httpClient.get<Array<MateriaEstudiante>>('/api/poll/userApprovedSubjects/' + idEstudiante);
  }

  actualizarMateriasAprobadas(idEstudiante: string, materiasAprobadas) {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.httpClient.post('/api/poll/userApprovedSubjects/' + idEstudiante, materiasAprobadas, { headers });
  }

  obtenerMateriasDisponibles(idEstudiante: string) {
    return this.httpClient.get<Array<MateriaEstudiante>>('/api/poll/userDisapprovedSubjectsWithCommissionAvailable/' + idEstudiante);
  }

  enviarComisionesSeleccionadas(idEstudiante: string, comisionesSeleccionadas) {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.httpClient.post('/api/poll/comisionesSeleccionada/' + idEstudiante, comisionesSeleccionadas, {headers});
  }

  getCertificadoDePreinscripcion(idEstudiante: string) {
    return this.httpClient.get('/api/pdf/' + idEstudiante, { responseType: 'blob'});
  }

  ingresarUsuario(usuario: Usuario) {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.httpClient.post('/api/encuesta/ingresoUsuario', usuario, { headers });
  }

  getCarreras() {
    return this.httpClient.get<Array<Carrera>>('/api/carreras');
  }

  getMaterias() {
    return this.httpClient.get<Array<Materia>>('/api/materias');
  }

  actualizarInformacionMateria(informacionMateria: Materia) {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.httpClient.post('/api/materias/modificarMateria', informacionMateria, { headers });
  }

  agregarNuevaMateria(nuevaMateria: Materia) {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.httpClient.put('/api/materias/nuevaMateria', nuevaMateria, { headers });
  }

  actualizarInformacionCarrera(carreraActualizada: Carrera) {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.httpClient.post('/api/carreras/actualizarCarrera', carreraActualizada, { headers });
  }

  agregarNuevaCarrera(nuevaCarrera: Carrera) {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.httpClient.put('/api/carreras/nuevaCarrera', nuevaCarrera, { headers });
  }

  getOfertas() {
    return this.httpClient.get<Array<OfertaAcademica>>('/api/ofertas-academicas');
  }

  clonarOferta(oferta: OfertaAcademica) {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.httpClient.put('/api/ofertas-academicas/clonarOferta/', oferta, { headers });
  }

  crearNuevaOferta(nuevaOferta: OfertaAcademica) {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.httpClient.put('/api/ofertas-academicas/crearOferta/', nuevaOferta, { headers });
  }

  actualizarInformacionDeOferta(ofertaActualizada: OfertaAcademica) {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.httpClient.post('/api/ofertas-academicas/actualizarOferta/', ofertaActualizada, { headers });
  }

  getMateriasDeCarrera(idCarrera: string) {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.httpClient.get<Array<Materia>>('/api/oferta-academica/materias/' + idCarrera);
  }

  getComisionesDeOferta(idOferta: string) {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.httpClient.get<Array<Comision>>('/api/oferta-academica/comisiones/' + idOferta);
  }

  getPeriodos() {
    return this.httpClient.get<Array<Periodo>>('/api/periodos');
  }

  getTipoPeriodos() {
    return this.httpClient.get('/api/tipoPeriodos');
  }
}
