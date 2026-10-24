import {Injectable, signal} from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class Loading {
  loading = signal<boolean>(false);

  public isLoading = this.loading.asReadonly();

  public setLoaded(){
    this.loading.set(true);
  }
}
