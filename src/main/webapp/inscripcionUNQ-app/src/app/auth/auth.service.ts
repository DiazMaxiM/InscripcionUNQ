import { Injectable } from '@angular/core';
import 'rxjs/add/operator/filter';
import { environment } from '../../environments/environment';
import { UtilesService } from '../utiles.service';
import { tokenNotExpired } from 'angular2-jwt';
import Auth0Lock from 'auth0-lock';

@Injectable()
export class AuthService {

  auth0Options = {
    theme: {
      logo:'https://ddhh.unq.edu.ar/wp-content/uploads/2017/04/Logo-UNQ-RGB.png',
      primaryColor: '#DFA612'
    },
    languageDictionary: {
      emailInputPlaceholder: 'correo electrónico',
      passwordInputPlaceholder: 'contraseña',
      title: 'Pre inscripción CPI'
    },
    auth: {
      redirectUrl: environment.auth0.callbackURL,
      responseType: 'token id_token',
      audience: `https://${environment.auth0.domain}/userinfo`,
      params: {
        scope: 'openid profile'
      }
    },
    autoclose: true,
    oidcConformant: true,
    language: 'es',
    allowedConnections: ['google-oauth2']
  };

  lock: Auth0Lock = new Auth0Lock(
    environment.auth0.clientID,
    environment.auth0.domain,
    this.auth0Options
  );

  constructor(private utilesService: UtilesService) {

    this.lock.on('authenticated', (authResult: any) => {
      this.lock.getUserInfo(authResult.accessToken, (error, profile) => {
        if (error) {
          throw new Error(error);
        }
        localStorage.setItem('token', authResult.idToken);
        localStorage.setItem('profile', JSON.stringify(profile));
        this.logout();
      });
    });
  }

  public login(): void {
    this.lock.show();

  }

  logout() {
    localStorage.removeItem('profile');
    localStorage.removeItem('token');
  }


  public isAuthenticated() {
    return tokenNotExpired();
  }

}
