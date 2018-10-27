export class Usuario {

    email?: string;
    password?: string;
    id?: string;

  constructor(

    email?: string,
    password?: string,
    id?:string
  ) {
    this.email = email;
    this.password = password;
    this.id = id;
  }
}
