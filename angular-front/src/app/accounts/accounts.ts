import { Component , inject} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {AsyncPipe} from '@angular/common';
import {Account, AccountListState, RequestStatus} from '../../model/account.model';
import {catchError, map, Observable, of} from 'rxjs';

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
  //variable qui se termine par un '$' veut dire que c'est un objet observable

  //solution 2 : injection avec inject()
  private http= inject(HttpClient);
  //liste des comptes
  accounts$: Observable<AccountListState> = this.http.get<Account[]>
  ("http://localhost:9999/EBANK-SERVICE/accounts")
    .pipe(
      map(resp => {
        return {accounts:resp, status:RequestStatus.SUCCESS};
      }),
      catchError((err,caught) => {
        return of({ status : RequestStatus.ERROR, errorMessage : err.statusText });
      })
    );
  protected readonly RequestStatus = RequestStatus;
}
