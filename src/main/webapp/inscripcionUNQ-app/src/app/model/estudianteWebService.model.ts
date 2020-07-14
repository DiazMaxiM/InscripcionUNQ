import { DatosPersonalesEstudianteWebService } from "./datosPersonalesEstudianteWebService.model";
import { Carrera } from "../carreras/carrera.model";

export class EstudianteWebService {
    id?: string;
    datos_personales: DatosPersonalesEstudianteWebService;
    carreras: Carrera[];

    constructor(
        datos?: DatosPersonalesEstudianteWebService,
        carreras?: Carrera[],
	) {
		this.datos_personales = datos;
        this.carreras= carreras;
	}

    

    
}
