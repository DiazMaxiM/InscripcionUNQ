export class Estudiante {

  email: string;
  nombre: string;
  apellido: string;
  dni: string;
  id: string;
  constructor(
    dni: string,
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
