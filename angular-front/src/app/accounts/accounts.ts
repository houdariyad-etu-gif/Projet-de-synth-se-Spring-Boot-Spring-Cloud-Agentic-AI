import { Component , inject} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {AsyncPipe} from '@angular/common';

@Component({
  selector: 'app-accounts',
  imports: [
    AsyncPipe
  ],
  templateUrl: './accounts.html',
  styleUrl: './accounts.css',
})
export class Accounts {
  //solution 1 : injection classique
  // constructor(private http:HttpClient) {
  // }

  //solution 2 : injection avec inject()
  private http= inject(HttpClient);
  //liste des comptes
  //variable qui se termine par un '$' veut dire que c'est un objet observable
  accounts$ = this.http.get<any[]>("http://localhost:9999/EBANK-SERVICE/accounts")
}
