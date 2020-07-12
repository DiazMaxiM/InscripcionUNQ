import { Component} from '@angular/core';
import { RestService } from '../rest.service';
import {UtilesService} from '../utiles.service';

@Component({
  selector: 'app-tareas-usuario',
  templateUrl: './tareas-usuario.component.html',
  styleUrls: ['./tareas-usuario.component.css']
})
export class TareasUsuarioComponent{

  constructor(
    private restService: RestService,
    private utilesService: UtilesService
  ) {}

  archivoSeleccionado(event){
		const archivo = <File> event.target.files[0];
        let reader = new FileReader();
        reader.onload = () => {
            // this 'text' is the content of the file
			var text = reader.result;
			this.guardarArchivo(text)
        }
		reader.readAsText(archivo);

	}

	guardarArchivo(text){
		this.restService.guardarArchivo(text).subscribe(() => {
			this.utilesService.mostrarMensaje("El archivo fue guardado con exito")
		},
			(err) => {
				this.utilesService.mostrarMensajeDeError(err);
		});
  }
  

}