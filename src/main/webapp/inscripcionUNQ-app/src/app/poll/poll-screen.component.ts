import { Component, OnInit} from '@angular/core';
import { PollService } from './poll.service';
import {PollInfo} from './poll-info.model';
import {UtilesService} from '../utiles.service';

@Component({
  selector: 'app-poll-screen',
  templateUrl: './poll-screen.component.html',
  styleUrls: ['./poll-screen.component.css']
})
export class PollScreenComponent implements OnInit {
  constructor(
  private pollService: PollService,
  private utilesService: UtilesService
) { }
  pollInfo: PollInfo;
  polls: any[];

  ngOnInit() {
    this.pollService.currentPollInfo.subscribe((pollInfo:PollInfo) => {
      this.pollInfo = pollInfo;
      this.polls = pollInfo.polls;
    });
  }

  editPoll(poll) {
    const fechaDeFinalizacion = this.utilesService.crearFecha(poll.endDate);
    const fechaDeHoy = new Date();
    if (fechaDeFinalizacion >= fechaDeHoy) {
      this.pollInfo.idCurrentPoll = poll.id;
      this.pollService.sendStudentPollInfo(this.pollInfo);
      this.utilesService.irA('verificacion-de-datos');

    } else {
       this.informarEstadoEncuesta();
    }
  }

  informarEstadoEncuesta() {
    const mensaje = 'La encuesta no se encuentra vigente';
    if (this.polls.length > 1) {
      this.utilesService.mostrarMensaje(mensaje);
    } else {
      this.utilesService.mostrarMensajeYSalir(mensaje);
    }
  }
}
