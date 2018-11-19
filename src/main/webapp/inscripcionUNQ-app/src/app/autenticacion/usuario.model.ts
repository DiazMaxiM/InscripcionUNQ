export class Usuario {
	email?: string;
	password?: string;
	id?: string;
	dni?: number;
	perfiles?: string[];
	nombre?: string;
	apellido?: string;

	constructor(
		email?: string,
		nombre?: string,
		apellido?: string,
		dni?: number,
		password?: string,
		id?: string,
	) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.password = password;
		this.dni = dni;
		this.id = id;
	}
}