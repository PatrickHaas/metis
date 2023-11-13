import {Component, Input} from '@angular/core';

@Component({
  selector: 'page-spinner',
  templateUrl: './page-spinner.component.html',
  styleUrls: ['./page-spinner.component.scss']
})
export class PageSpinnerComponent {
  @Input()
  text: string = 'Lädt...';
}
