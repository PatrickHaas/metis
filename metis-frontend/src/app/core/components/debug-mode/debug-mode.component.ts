import {Component} from '@angular/core';
import {DebugService} from "../../services/debug.service";

@Component({
  selector: 'debug-mode',
  templateUrl: './debug-mode.component.html',
  styleUrls: ['./debug-mode.component.scss']
})
export class DebugModeComponent {

  debugMode: boolean = false;

  constructor(private debugService: DebugService) {
    this.debugService.debugModeObservable.subscribe(debugMode => this.debugMode = debugMode);
  }

  toggleDebugMode() {
    this.debugService.toggleDebugMode();
  }

}
