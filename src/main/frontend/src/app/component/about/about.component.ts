import {Component, OnInit} from '@angular/core';
import {AboutService} from '../../service/about.service';
import {AlertService} from '../../service/alert.service';
import {AuthService} from '../../service/auth.service';

@Component({
  selector: 'app-about',
  templateUrl: './about.component.html'
})
export class AboutComponent implements OnInit {

  appVersion = '';

  constructor(private aboutService: AboutService,
              private alertService: AlertService,
              private authService: AuthService) {
  }

  ngOnInit() {
    this.aboutService.getAppVersion().subscribe(
      value => {
        this.appVersion = value.text();
      },
      error => {
        console.log('Error occurred: ' + error);
        this.alertService.error('Cannot get Application version.');
      }
    );
  }

  getLoggedUserName(): string {
    const user = this.authService.getLoggedUser();
    if (user != null) {
      return user.username;
    } else {
      return '';
    }
  }
}
