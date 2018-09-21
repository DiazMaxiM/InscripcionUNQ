import { Component} from '@angular/core';
import { RestService } from '../rest.service';
import {UtilesService} from '../utiles.service';

@Component({
  selector: 'app-encuesta-finalizada',
  templateUrl: './encuesta-finalizada.component.html',
  styleUrls: ['./encuesta-finalizada.component.css', '../app.component.css']
})
export class EncuestaFinalizadaComponent {

  constructor(
    private restService: RestService,
    private utilesService: UtilesService
  ) {}


  descargarCertificado() {
    const idEstudiante  = localStorage.getItem('idEstudiante');
    this.restService.getCertificadoDePreinscripcion(idEstudiante).subscribe(data => {
      this.descargarArchivo(data);
    });
  }

  descargarArchivo(pdf) {
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
           // html5 download no soportado
           const mensaje = 'No se puede descargar el certificado desde tú navegador';
           this.utilesService.mostrarMensajeYSalir(mensaje);

       }
   }
  }
  salir() {
    localStorage.clear();
    this.utilesService.salir();
  }
}
