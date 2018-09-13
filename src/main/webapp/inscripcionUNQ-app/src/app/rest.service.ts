import { Injectable } from '@angular/core';
import { map } from 'rxjs/operators';
import { Http, Headers, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { Student } from './data-verification/student.model';

@Injectable()
export class RestService {

  constructor(private http: Http) {}

  getPolls(idStudent: number) {
    const headers = new Headers({ 'Content-Type': 'application/json' });
    return this.http.get('/api/poll/user/' + idStudent, { headers })
    .pipe(
        map((response: Response) => response.json())
    );
  }

  getStudentData(dniStudent: number, idPoll: number) {
  const headers = new Headers({ 'Content-Type': 'application/json' });
  return this.http.get('/api/poll/userData/' + dniStudent + '/' + idPoll, { headers })
  .pipe(
      map((response: Response) => response.json())
  );
}

updateStudentData(studentData: Student) {
  const headers = new Headers({ 'Content-Type': 'application/json' });
  return this.http.post('/api/poll/userData', studentData, { headers })
  .pipe(
      map((response: Response) => response['status'])
    );
}

getSubjets(idStudent: number) {
  const headers = new Headers({ 'Content-Type': 'application/json' });
  return this.http.get('/api/poll/userApprovedSubjects/' + idStudent, { headers })
  .pipe(
      map((response: Response) => response.json())
  );
}

updateStubjets(idStudent: number, subjects) {
  const headers = new Headers({ 'Content-Type': 'application/json' });
  return this.http.post('/api/poll/userApprovedSubjects/' + idStudent, subjects, { headers })
  .pipe(
      map((response: Response) => response['status'])
    );
}

obtenerMateriasDisponibles(idStudent: number) {
  return this.http.get('/api/poll/userDisapprovedSubjectsWithCommissionAvailable/' + idStudent)
  .pipe(
      map((response: Response) => response.json())
  );
}

}
