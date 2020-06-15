import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable()
export class UsuarioLogueadoService {
   private usuarioLogueado = new BehaviorSubject<boolean>(false);

   getUsuarioLogueado(): Observable<boolean> {
      return this.usuarioLogueado.asObservable();
   }

   notificarUsuarioLoguedado() {
      this.usuarioLogueado.next(true);
   }

   private perfilUsuarioLogueado: BehaviorSubject<string> = new BehaviorSubject<string>(null);

   getPerfilUsuarioLogueado(): Observable<string> {
      return this.perfilUsuarioLogueado.asObservable();
   }

   notificarPerfilUsuarioLogueado(perfil: string) {
      this.perfilUsuarioLogueado.next(perfil);
   }
}