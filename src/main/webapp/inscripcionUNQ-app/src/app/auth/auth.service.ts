
import { Injectable } from '@angular/core';
import { map } from 'rxjs/operators';
import { Http, Response } from '@angular/http';
import {Poll} from '../student-poll/poll.model';

@Injectable()
export class AuthService {
  constructor(private http: Http) {}

  login(dni: String) {
    return this.http.get('/api/poll/user/' + dni)
    .pipe(
        map((res: Response) => res.json())
    );
  }
}
