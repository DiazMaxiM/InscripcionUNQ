import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { MatDialogRef, MatDialog } from '@angular/material';
import { TipoIncidenciaDialogoComponent } from './tipo-incidencia-dialogo/tipo-incidencia-dialogo.component';
import { TipoIncidencia } from './tipo-incidencia-dialogo/tipo-incidencia.model';

@Injectable()
export class DialogosService {

    constructor(private dialog: MatDialog) { }

    public abrirDialogoTipoDeIncidencia(tipoIncidencia?: TipoIncidencia): Observable<boolean> {

        let dialogRef: MatDialogRef<TipoIncidenciaDialogoComponent>;

				dialogRef = this.dialog.open(TipoIncidenciaDialogoComponent);
				dialogRef.componentInstance.tipoDeIncidencia = tipoIncidencia;
				
        return dialogRef.afterClosed();
    }
}