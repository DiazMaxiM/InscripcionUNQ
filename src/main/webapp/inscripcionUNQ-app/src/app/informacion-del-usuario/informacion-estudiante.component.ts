import { Component, OnInit} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RestService } from '../rest.service';
import { Estudiante } from './estudiante.model';
import {UtilesService} from '../utiles.service';
import {HttpErrorResponse } from '@angular/common/http';

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
  dniEstudiante: string;
  idEncuestaActual: string;

ngOnInit() {
    this.dniEstudiante = localStorage.getItem('dniEstudiante');
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
    this.restService.getInformacionEstudiante(this.dniEstudiante, this.idEncuestaActual)
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
    const studentData = new Estudiante(this.dniEstudiante, nombres, apellidos, email, this.idEstudiante);
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
