import { Injectable} from '@angular/core';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';

@Injectable()
export class usuarioLogueadoService  {
  private usuarioLogueado = new BehaviorSubject<boolean>(false);
  hayUsuarioLogueaado = this.usuarioLogueado.asObservable();
  constructor() { }

 notificarUsuarioLoguedado() {
    this.usuarioLogueado.next(true);
 }
}
