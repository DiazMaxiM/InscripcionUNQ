import { Component, OnInit } from '@angular/core';
import { UtilesService } from 'src/app/utiles.service';
import { AppRutas } from 'src/app/app-rutas.model';

@Component({
  selector: 'app-navegacion-estudiante',
  templateUrl: './navegacion-estudiante.component.html',
  styleUrls: ['./navegacion-estudiante.component.css']
})
export class NavegacionEstudianteComponent implements OnInit {

  constructor(
		private utilesService: UtilesService
	) { }

  ngOnInit(): void {
  }

  irAEncuestas(){
    this.utilesService.irA(AppRutas.ENCUESTAS_VIGENTES);
  }

  irACuenta(){

  }

  irAIncidencias(){
    
  }
}
