import {Component} from '@angular/core';
import {AuthService} from '../../service/auth.service';
import {Router} from '@angular/router';
import {AlertService} from '../../service/alert.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html'
})
export class HeaderComponent {

  constructor(private authService: AuthService,
              private router: Router,
              private alertService: AlertService) {
  }

  logout() {
    this.authService.logout().subscribe(
      () => {
        this.router.navigate(['/home']);
      },
      error => {
        console.log('Error occurred: ' + error);
        this.alertService.error('Something went wrong.');
      }
    );
  }

  isUserLoggedIn(): boolean {
    return this.authService.isUserLoggedIn();
  }
}
