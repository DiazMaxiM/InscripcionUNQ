import { Component, OnInit} from '@angular/core';
import {UtilesService} from '../utiles.service';
import { Usuario } from '../autenticacion/usuario.model';
import { RestService } from '../rest.service';

@Component({
  selector: 'app-encuestas-disponibles',
  templateUrl: './encuestas-disponibles.component.html',
  styleUrls: ['./encuestas-disponibles.component.css']
})
export class EncuestasDisponiblesComponent implements OnInit {
  constructor(
	private utilesService: UtilesService,
	private restService: RestService
) { }
	encuestas: any[];
	encuestaBuscada: string;
	usuario: Usuario;

  ngOnInit() {
		this.usuario = JSON.parse(localStorage.getItem('usuario'));
		this.getEncuestas();
	}

  getEncuestas() {
		this.restService.getEncuestasVigentes(this.usuario.dni).subscribe(encuestas => {
			this.encuestas = encuestas;
      if (this.encuestas.length == 0) {
        const mensaje = 'No se encontraron encuestas para el email ingresado';
				this.utilesService.mostrarMensajeYSalir(mensaje);
	
		}
	});
}

  editarEncuesta(idEncuestaActual) {
      localStorage.setItem('idEncuestaActual', idEncuestaActual);
      this.utilesService.irA('verificacion-de-datos');
  }
}
