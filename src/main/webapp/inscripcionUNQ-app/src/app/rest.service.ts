import { Injectable } from '@angular/core';
import { Estudiante } from './informacion-del-usuario/estudiante.model';
import { Materia } from './materias-aprobadas/materia.model';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Usuario } from './autenticacion/usuario.model';
import { Carrera } from './carreras/carrera.model';

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
    return this.httpClient.get<Array<Materia>>('/api/poll/userApprovedSubjects/' + idEstudiante);
  }

  actualizarMateriasAprobadas(idEstudiante: string, materiasAprobadas) {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.httpClient.post('/api/poll/userApprovedSubjects/' + idEstudiante, materiasAprobadas, { headers });
  }

  obtenerMateriasDisponibles(idEstudiante: string) {
    return this.httpClient.get<Array<Materia>>('/api/poll/userDisapprovedSubjectsWithCommissionAvailable/' + idEstudiante);
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

  getCarreras() {
    return this.httpClient.get<Array<Carrera>>('/api/carreras');
  }

  eliminarCarrera(idCarrera: string) {
    return this.httpClient.delete('/api/carreras/eliminarCarrera/' + idCarrera);
  }

    actualizarInformacionMateria(informacionMateria: Materia) {
      const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
      return this.httpClient.post('/api/materia', informacionMateria, { headers });
    }
  }
