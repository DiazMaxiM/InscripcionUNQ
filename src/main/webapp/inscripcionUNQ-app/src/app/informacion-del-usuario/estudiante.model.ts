export class Estudiante {

  email: string;
  nombre: string;
  apellido: string;
  dni: number;
  id: string;
  constructor(
    dni: number,
    nombre: string,
    apellido: string,
    email: string,
    id: string
  ) {
    this.dni = dni;
    this.nombre = nombre;
    this.apellido = apellido;
    this.email = email;
    this.id = id;
  }
}
