import { Injectable } from '@angular/core';
import { map } from 'rxjs/operators';
import { Observable } from 'rxjs/Observable';
import { Student } from './data-verification/student.model';
import { Subject } from './subject/subject.model';
import {HttpClient, HttpHeaders} from '@angular/common/http';

@Injectable()
export class RestService {

  constructor(private httpClient: HttpClient) {}

  getPolls(idStudent: number) {
    return this.httpClient.get('/api/poll/user/' + idStudent);
  }

  getStudentData(dniStudent: number, idPoll: number) {
    return this.httpClient.get('/api/poll/userData/' + dniStudent + '/' + idPoll);
}

updateStudentData(studentData: Student) {
  const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
  return this.httpClient.post('/api/poll/userData', studentData, { headers });
}

getSubjets(idStudent: number) {
  return this.httpClient.get<Array<Subject>>('/api/poll/userApprovedSubjects/' + idStudent);
}

updateStubjets(idStudent: number, subjects) {
  const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
  return this.httpClient.post('/api/poll/userApprovedSubjects/' + idStudent, subjects, { headers });
}

obtenerMateriasDisponibles(idStudent: number) {
  return this.httpClient.get<Array<Subject>>('/api/poll/userDisapprovedSubjectsWithCommissionAvailable/' + idStudent);
}

enviarComisionesSeleccionadas(idStudent: number, comisiones) {
  const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
  return this.httpClient.post('/api/poll/comisionesSeleccionada/' + idStudent, comisiones, {headers});
}

getCertificadoDePreinscripcion(idStudent: number) {
  return this.httpClient.get('/api/pdf/' + idStudent, { responseType: 'blob'});
}

}
