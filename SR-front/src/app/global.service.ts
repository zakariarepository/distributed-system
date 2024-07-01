import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class GlobalService {

  constructor(
    private http: HttpClient
  ) { }


  sendToServer(n:number) {
    return this.http.get("http://localhost:4000/api",{
      params:{
        inputNum:n
      }
    });
  }
}
