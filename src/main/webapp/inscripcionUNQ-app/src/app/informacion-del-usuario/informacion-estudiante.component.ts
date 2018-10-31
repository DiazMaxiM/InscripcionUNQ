import { Component, OnInit} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RestService } from '../rest.service';
import { Estudiante } from './estudiante.model';
import {UtilesService} from '../utiles.service';
import {HttpErrorResponse } from '@angular/common/http';
import { Usuario } from '../autenticacion/usuario.model';

@Component({
  selector: 'app-informacion-estudiante',
  templateUrl: './informacion-estudiante.component.html',
  styleUrls: ['./informacion-estudiante.component.css']
})
export class InformacionEstudianteComponent implements OnInit {

  constructor(
    private restService: RestService,
    private formBuilder: FormBuilder,
    private utilesService: UtilesService
  ) {}

  informacionEstudianteForm: FormGroup;
  idEstudiante: string;
  usuario: Usuario;
  idEncuestaActual: string;

ngOnInit() {
    this.usuario = JSON.parse(localStorage.getItem('usuario'));
    this.idEncuestaActual = localStorage.getItem('idEncuestaActual');
    this.getInformacionEstudiante();
    this.crearFormularioEstudiante();
  }

crearFormularioEstudiante() {
    this.informacionEstudianteForm = this.formBuilder.group({
          nombres: ['', Validators.required],
          apellidos: ['', Validators.required],
          email: ['', [Validators.required, Validators.email]]
      });
  }

  getInformacionEstudiante() {
    this.restService.getInformacionEstudiante(this.usuario.dni, this.idEncuestaActual)
    .subscribe(data =>
      this.insertarInformacionEstudianteEnFormulario(data)
    );
  }

insertarInformacionEstudianteEnFormulario(estudiante) {
  this.idEstudiante = estudiante.id;
  localStorage.setItem('idEstudiante', this.idEstudiante);
  this.informacionEstudianteForm.setValue({
    'nombres': estudiante.nombre,
    'apellidos': estudiante.apellido,
    'email': estudiante.email
  });
}

onSubmit() {
  if (this.informacionEstudianteForm.valid) {
    const { nombres, apellidos, email} = this.informacionEstudianteForm.value;
    const studentData = new Estudiante(this.usuario.dni, nombres, apellidos, email, this.idEstudiante);
    this.restService.actualizarInformacionEstudiante(studentData)
      .subscribe(res => {
        const mensaje = 'Los datos fueron actualizados con exito';
         this.utilesService.mostrarMensajeYRedireccionar(mensaje, 'materias-aprobadas');

       },
       (err: HttpErrorResponse) => {
         this.utilesService.mostrarMensajeDeError(err);
       });
  }
}

}
