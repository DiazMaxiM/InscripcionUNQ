import { Component} from '@angular/core';
import { RestService } from '../rest.service';
import {UtilesService} from '../utiles.service';


@Component({
  selector: 'app-tareas-usuario',
  templateUrl: './tareas-usuario.component.html',
  styleUrls: ['./tareas-usuario.component.css']
})
export class TareasUsuarioComponent {
    constructor(
      private restService: RestService,
      private utilesService: UtilesService
    ) {}

    irACarreras() {

      this.restService.getCarreras().subscribe(carreras => {
        localStorage.setItem('carreras',JSON.stringify(carreras));
        this.utilesService.irA('carreras');
      },
      (err) => {
          this.utilesService.mostrarMensajeDeError(err);
      });
    }

    irAMaterias() {
      this.restService.getMaterias().subscribe(materias => {
        localStorage.setItem('materias',JSON.stringify(materias));
        this.utilesService.irA('materias');
      },
      (err) => {
          this.utilesService.mostrarMensajeDeError(err);
      });
    }


    irAOfertaAcademica() {

      this.restService.getOfertas().subscribe(ofertas => {
        console.log(ofertas);
        localStorage.setItem('ofertas', JSON.stringify(ofertas));
        this.utilesService.irA('oferta-academica');
      },
      (err) => {
          this.utilesService.mostrarMensajeDeError(err);
      });
    }



}
