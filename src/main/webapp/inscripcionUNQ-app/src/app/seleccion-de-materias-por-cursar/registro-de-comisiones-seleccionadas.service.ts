import { Injectable } from '@angular/core';
import { Horario } from '../seleccion-de-comision-dialogo/horario.model';
import { ComisionSeleccionada } from '../seleccion-de-comision-dialogo/comision-seleccionada.model';

@Injectable()
export class RegistroDeComisionesSeleccionadasService {

  private horariosOcupados = [];

  constructor() {}

  crearRegistroDeComisionSeleccionada(idMateria, comision) {
    const nuevoRegistro: ComisionSeleccionada = this.nuevoRegistro(idMateria, comision);
    if (!this.hayHorariosSuperpuestos(nuevoRegistro.horariosSeleccionados)) {
      for (const horario of nuevoRegistro.horariosSeleccionados) {
      this.horariosOcupados.push(horario);

    }
    return nuevoRegistro;
  }
  nuevoRegistro.horariosSeleccionados = [];
  return nuevoRegistro;
}

hayHorariosSuperpuestos(horariosPorOcupar) {
   let haySuperposicion = false;
    for (const horarioPorOcupar of horariosPorOcupar) {
      for (const horarioOcupado of this.horariosEnElMismoDia(horarioPorOcupar)) {
         haySuperposicion = haySuperposicion || this. esHorarioSuperpuesto(horarioOcupado, horarioPorOcupar);
      }
    }
    return haySuperposicion;

}

horariosEnElMismoDia(horarioPorOcupar: Horario) {
  return this.horariosOcupados.filter(horarioDeLaComision => horarioDeLaComision.dia == horarioPorOcupar.dia);

}


  public guardarHorario(horario: Horario) {
    this.horariosOcupados.push(horario);
  }
  public eliminarHorarios(horarios: Horario[] ) {
    for (const horario of horarios)  {
        const index = this.horariosOcupados.indexOf(horario);
        this.horariosOcupados.splice(index, 1);
    }
}

 nuevoRegistro(idMateria, comision) {
   const registro = new ComisionSeleccionada(comision.id, comision.nombre, idMateria);
   for (const horario of comision.horarioJson) {
     const horarioSeleccionado = this.crearHorario(horario);
     registro.agregarHorario(horarioSeleccionado);
  }
    return registro;
  }

nuevoHorario(horario) {
  const date = new Date();
  date.setHours(horario.hour);
  date.setMinutes(horario.minute);
  date.setSeconds(horario.second);
  return date;
}

crearHorario(horario) {
  const horaDeInicio = this.nuevoHorario(horario.horaComienzo);
  const horaDeFin = this.nuevoHorario(horario.horaFin);

  return new Horario(horario.dia, horaDeInicio, horaDeFin);
}

 esHorarioSuperpuesto(horarioOcupado: Horario, horarioDeConsulta: Horario) {
  let haySuperposicion = false;

  if (horarioOcupado.dia == horarioDeConsulta.dia) {
      haySuperposicion = this.haySuperposicionEntreHorarios(horarioOcupado, horarioDeConsulta);
  }
  return haySuperposicion;
}

haySuperposicionEntreHorarios(horarioOcupado: Horario, horarioDeConsulta: Horario) {
  return horarioOcupado.horarioDeComienzo <= horarioDeConsulta.horarioDeFinalizacion &&
  horarioOcupado.horarioDeFinalizacion >= horarioDeConsulta.horarioDeComienzo;
}

limpiarHorarios() {
  this.horariosOcupados = [];
}

}
