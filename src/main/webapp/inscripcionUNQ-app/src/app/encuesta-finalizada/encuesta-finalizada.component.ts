import { Component, OnInit} from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { RestService } from '../rest.service';
import { PollService } from '../poll/poll.service';
import { PollInfo } from '../poll/poll-info.model';
import {HttpErrorResponse } from '@angular/common/http';
import {UtilesService} from '../utiles.service';

@Component({
  selector: 'app-encuesta-finalizada',
  templateUrl: './encuesta-finalizada.component.html',
  styleUrls: ['./encuesta-finalizada.component.css']
})
export class EncuestaFinalizadaComponent implements OnInit {

  pollInfo: PollInfo;

  constructor(
    private restService: RestService,
    private pollService: PollService,
    private utilesService: UtilesService
  ) {}

  ngOnInit() {
    this.pollService.currentPollInfo.subscribe((pollInfo: PollInfo) => {
        this.pollInfo = pollInfo;
      });
    }

  descargarCertificado(){
    this.restService.getCertificadoDePreinscripcion(this.pollInfo.idStudent).subscribe(data => {
      this.descargarArchivo(data);
    });
  }

  descargarArchivo(pdf){
    const nombreDeArchivo = 'certificado-de-Pre-Inscripcion.pdf';
    if (navigator.msSaveBlob) {
       // IE 10+
       navigator.msSaveBlob(pdf, nombreDeArchivo);
   } else {
       const link = document.createElement('a');
       if (link.download !== undefined) {
           const url = URL.createObjectURL(pdf);
           link.setAttribute('href', url);
           link.setAttribute('download', nombreDeArchivo);
           link.style.visibility = 'hidden';
           document.body.appendChild(link);
           link.click();
           document.body.removeChild(link);
       } else {
           // html5 download not supported
       }
   }
  }
  salir() {
    this.utilesService.salir();
  }
}
