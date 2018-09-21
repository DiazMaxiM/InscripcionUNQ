import { Component, OnInit} from '@angular/core';
import {UtilesService} from '../utiles.service';

@Component({
  selector: 'app-poll-screen',
  templateUrl: './poll-screen.component.html',
  styleUrls: ['./poll-screen.component.css']
})
export class PollScreenComponent implements OnInit {
  constructor(
  private utilesService: UtilesService
) { }
  polls: any[];

  ngOnInit() {
      this.polls = JSON.parse(localStorage.getItem('encuestasVigentes'));
  }

  editPoll(idEncuestaActual) {
      localStorage.setItem('idEncuestaActual', idEncuestaActual);
      this.utilesService.irA('verificacion-de-datos');
  }
}
