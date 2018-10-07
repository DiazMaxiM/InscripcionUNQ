import {Component, OnInit} from '@angular/core';
import { MatDialogRef} from '@angular/material';
import {FormBuilder, Validators, FormGroup} from '@angular/forms';
import { UtilesService } from '../utiles.service';
import { RestService } from '../rest.service';

@Component({
    selector: 'app-periodo-dialogo',
    templateUrl: './periodo-dialogo.component.html',
    styleUrls: ['../dialogo-abm.component.css']
})

export class PeriodoDialogoComponent implements OnInit {

    form: FormGroup;
    tipoPeriodos;


    constructor(
        private fb: FormBuilder,
        private utilesService: UtilesService,
        private dialogRef: MatDialogRef<PeriodoDialogoComponent>,
        private restService: RestService ) {
    }
    ngOnInit() {
        this.crearFormularioComision();
        this.tipoPeriodos();

    }

    getTipoPeriodos(){
      this.restService.getTipoPeriodos().subscribe(periodos => {
          this.tipoPeriodos = periodos;
      });
    }



    crearFormularioComision() {
        this.form = this.fb.group({
            anho: ['', Validators.required],
            numero: ['', Validators.required],
            tipoPeriodo: ['', Validators.required]
        });
    }

    guardar() {
        if (this.form.valid) {
        }
    }

    cerrar() {
        this.dialogRef.close();
    }

}
