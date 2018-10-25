import {Component, OnInit} from '@angular/core';
import { MatDialogRef} from '@angular/material';
import { FormBuilder, Validators, FormGroup} from '@angular/forms';
import { UtilesService } from '../utiles.service';
import { RestService } from '../rest.service';
import { Periodo } from '../periodos/periodo.model';


@Component({
    selector: 'app-periodo-dialogo',
    templateUrl: './periodo-dialogo.component.html',
    styleUrls: ['../dialogo-abm.component.css']
})

export class PeriodoDialogoComponent implements OnInit {
    form: FormGroup;
    tipoPeriodos: string[] = [];
    anhoActual: any = new Date();
    
    constructor(
        private fb: FormBuilder,
        private utilesService: UtilesService,
        private dialogRef: MatDialogRef<PeriodoDialogoComponent>,
        private restService: RestService ) {
    }

    ngOnInit() {
        this.anhoActual = this.anhoActual.getFullYear() + 5;
        this.crearFormularioComision();
        this.getTipoPeriodos();
    }

    getTipoPeriodos() {
        if (this.tipoPeriodos.length == 0) {
            this.restService.getTipoPeriodos().subscribe(periodos => {
                this.tipoPeriodos = periodos;
            });
        }
    }

    crearFormularioComision() {
        this.form = this.fb.group({
            anho : ['', [Validators.required, Validators.min(1989), Validators.max(this.anhoActual)]],
            numero: ['', Validators.required],
            tipoPeriodo: ['', Validators.required]
        });
    }

    guardar() {
        if (this.form.valid) {
             const { anho, numero, tipoPeriodo} = this.form.value;
            const periodo = new Periodo(anho, numero, tipoPeriodo);
            this.dialogRef.close(periodo);
        }else {
            this.utilesService.validateAllFormFields(this.form);
        }
    }

    cerrar() {
        this.dialogRef.close();
    }

}
