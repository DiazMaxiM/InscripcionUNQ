import { Component, OnInit, ViewChild} from '@angular/core';
import { RestService } from '../rest.service';
import {PageEvent} from '@angular/material';
import { Subject } from './subject.model';
import {UtilesService} from '../utiles.service';

@Component({
  selector: 'app-subject-screen',
  templateUrl: './subject-screen.component.html',
  styleUrls: ['./subject-screen.component.css']
})
export class SubjectScreenComponent implements OnInit {

  subjects: Subject[] = [];
  activePageDataSubjects = [];
  idEstudiante: string;

  // MatPaginator Inputs
 length = 0;
 pageSize = 10;
 pageSizeOptions: number[] = [5, 10];

 // MatPaginator Output
 pageEvent: PageEvent;

  constructor(
    private restService: RestService,
    private utilesService: UtilesService
  ) {}

  ngOnInit() {
        this.idEstudiante = localStorage.getItem('idEstudiante');
        this.getSubjets();
    }

  getSubjets() {
    this.restService.getMateriasAprobadas(this.idEstudiante)
    .subscribe(subjects => {
      this.subjects = subjects;
      this.length = this.subjects.length;
      this.activePageDataSubjects = this.subjects.slice(0, this.pageSize);
    }, (err) => {
      this.utilesService.mostrarMensajeDeError(err);
   });
  }

    updateStubjets() {
      this.utilesService.activarDialogoCargando();
      this.restService.actualizarMateriasAprobadas(this.idEstudiante, this.subjects)
      .subscribe(res => {
        this.utilesService.desactivarDialogoCargandoYRedireccionar('materias-por-cursar');
      }, (err) => {
        this.utilesService.mostrarMensajeDeError(err);
     });
    }

    update(id) {
      const result = [];
      for (const i in this.subjects) {
        if (this.subjects[i].id === id) {
          result.push({
            'id': this.subjects[i].id,
            'code': this.subjects[i].code,
            'name': this.subjects[i].name,
            'approved': !this.subjects[i].approved
          });
        } else {
          result.push(this.subjects[i]);
        }
      }
      this.subjects = result;
    }

  onPageChanged(e) {
    const firstCut = e.pageIndex * e.pageSize;
    const secondCut = firstCut + e.pageSize;
    this.activePageDataSubjects = this.subjects.slice(firstCut, secondCut);
}
setPageSizeOptions(setPageSizeOptionsInput: string) {
  this.pageSizeOptions = setPageSizeOptionsInput.split(',').map(str => +str);
}
}
