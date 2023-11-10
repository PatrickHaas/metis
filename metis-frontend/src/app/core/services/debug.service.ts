import {Injectable} from '@angular/core';
import {Observable, Subject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class DebugService {

  private debugModeSubject = new Subject<boolean>();
  debugMode: boolean = false;


  constructor() {
    this.debugModeObservable.subscribe(debugMode => this.debugMode = debugMode);
  }

  get debugModeObservable(): Observable<boolean> {
    return this.debugModeSubject.asObservable();
  }

  toggleDebugMode() {
    this.debugModeSubject.next(!this.debugMode);
  }

}
