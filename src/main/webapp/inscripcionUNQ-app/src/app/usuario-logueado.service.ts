import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable()
export class UsuarioLogueadoService {
   private usuarioLogueado: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
   private perfilUsuarioLogueado: BehaviorSubject<string> = new BehaviorSubject<string>(null);
   private esPaginaLogin: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(true);
   
   getUsuarioLogueado(): Observable<boolean> {
      return this.usuarioLogueado.asObservable();
   }

   notificarUsuarioLoguedado() {
      this.usuarioLogueado.next(true);
   }

   getPerfilUsuarioLogueado(): Observable<string> {
      return this.perfilUsuarioLogueado.asObservable();
   }

   notificarPerfilUsuarioLogueado(perfil: string) {
      this.perfilUsuarioLogueado.next(perfil);
   }

   getEsPaginaLogin(): Observable<boolean> {
      return this.esPaginaLogin.asObservable();
   }

   notificarEsPaginaLogin(esPaginaLogin: boolean) {
      this.esPaginaLogin.next(esPaginaLogin);
   }
}