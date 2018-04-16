import {Injectable} from '@angular/core';
import {Headers, Http, RequestOptions} from '@angular/http';
import {User} from '../model/user.model';
import {environment} from '../../environments/environment';

export const AUTH_API = environment.apiUrl + '/auth';

@Injectable()
export class AuthService {

  authenticated = false;
  loggedUser: User;

  constructor(private http: Http) {
    this.refreshAuthenticatedUser();
  }

  refreshAuthenticatedUser() {
    this.getAuthenticatedUser().subscribe(data => {
        this.loggedUser = data.json();
        this.authenticated = true;
      },
      error => {
        console.log('User is not authenticated, error: ' + error);
        this.authenticated = false;
        this.loggedUser = null;
      });
  }

  getAuthenticatedUser() {
    const URL: string = AUTH_API + '/user';
    const headers = new Headers({'Content-Type': 'application/json'});
    const options = new RequestOptions({headers: headers});
    return this.http.get(URL, options);
  }

  login(username: string, password: string) {
    const URL: string = AUTH_API + '/login';
    const credentials = 'username=' + username + '&password=' + password;
    const headers = new Headers({'Content-Type': 'application/x-www-form-urlencoded'});
    const options = new RequestOptions({headers: headers});
    return this.http.post(URL, credentials, options);
  }

  logout() {
    this.authenticated = false;
    const URL: string = AUTH_API + '/logout';
    return this.http.post(URL, '');
  }

  isUserLoggedIn(): boolean {
    return this.authenticated;
  }

  getLoggedUser(): User {
    return this.loggedUser;
  }

}
