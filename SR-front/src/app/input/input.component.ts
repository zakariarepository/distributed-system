import { Component } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import { GlobalService } from '../global.service';

@Component({
  selector: 'app-input',
  templateUrl: './input.component.html',
  styleUrls: ['./input.component.css']
})
export class InputComponent {
  inputNumber!: number;
  outputNumber!: number;

  constructor(
    private service:GlobalService
  ) {}

  test(){
    this.service.sendToServer(this.inputNumber).subscribe(
      (val:any)=>this.outputNumber=val
    )
  }
}
