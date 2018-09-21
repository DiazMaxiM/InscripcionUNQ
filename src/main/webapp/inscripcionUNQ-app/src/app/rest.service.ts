import { Injectable } from '@angular/core';
import { map } from 'rxjs/operators';
import { Observable } from 'rxjs/Observable';
import { Student } from './data-verification/student.model';
import { Subject } from './subject/subject.model';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Usuario } from './autenticacion/usuario.model';

@Injectable()
export class RestService {

  constructor(private httpClient: HttpClient) {}

  getEncuestasVigentes(idStudent: number) {
    return this.httpClient.get('/api/poll/user/' + idStudent);
  }

  getInformacionEstudiante(dniStudent: string, idPoll: string) {
    return this.httpClient.get('/api/poll/userData/' + dniStudent + '/' + idPoll);
}

updateStudentData(studentData: Student) {
  const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
  return this.httpClient.post('/api/poll/userData', studentData, { headers });
}

getMateriasAprobadas(idStudent: string) {
  return this.httpClient.get<Array<Subject>>('/api/poll/userApprovedSubjects/' + idStudent);
}

actualizarMateriasAprobadas(idStudent: string, subjects) {
  const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
  return this.httpClient.post('/api/poll/userApprovedSubjects/' + idStudent, subjects, { headers });
}

obtenerMateriasDisponibles(idStudent: string) {
  return this.httpClient.get<Array<Subject>>('/api/poll/userDisapprovedSubjectsWithCommissionAvailable/' + idStudent);
}

enviarComisionesSeleccionadas(idStudent: string, comisiones) {
  const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
  return this.httpClient.post('/api/poll/comisionesSeleccionada/' + idStudent, comisiones, {headers});
}

getCertificadoDePreinscripcion(idStudent: string) {
  return this.httpClient.get('/api/pdf/' + idStudent, { responseType: 'blob'});
}


ingresarUsuario(usuario: Usuario) {
  const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
  return this.httpClient.post('/api/encuesta/ingresoUsuario', usuario, { headers });
}

}
