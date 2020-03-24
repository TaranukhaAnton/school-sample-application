import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISchollClass } from 'app/shared/model/scholl-class.model';

@Component({
  selector: 'jhi-scholl-class-detail',
  templateUrl: './scholl-class-detail.component.html'
})
export class SchollClassDetailComponent implements OnInit {
  schollClass: ISchollClass | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ schollClass }) => (this.schollClass = schollClass));
  }

  previousState(): void {
    window.history.back();
  }
}
