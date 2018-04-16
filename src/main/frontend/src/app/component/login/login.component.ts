import {Component, OnInit} from '@angular/core';
import {NgForm} from '@angular/forms';
import {Router} from '@angular/router';
import {AuthService} from '../../service/auth.service';
import {AlertService} from '../../service/alert.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html'
})
export class LoginComponent implements OnInit {

  constructor(private authService: AuthService,
              private alertService: AlertService,
              private router: Router) {
  }

  ngOnInit() {
  }

  login(form: NgForm) {
    const username = form.value.username;
    const password = form.value.password;
    this.authService.login(username, password).subscribe(
      () => {
        this.authService.refreshAuthenticatedUser();
        this.router.navigate(['/home']);
      },
      error => {
        console.log('login failed, error: ' + error);
        this.authService.refreshAuthenticatedUser();
        this.alertService.error('Wrong username or password!');
      }
    );
  }
}
