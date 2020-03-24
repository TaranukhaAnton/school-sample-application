import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGradeLevel } from 'app/shared/model/grade-level.model';

@Component({
  selector: 'jhi-grade-level-detail',
  templateUrl: './grade-level-detail.component.html'
})
export class GradeLevelDetailComponent implements OnInit {
  gradeLevel: IGradeLevel | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ gradeLevel }) => (this.gradeLevel = gradeLevel));
  }

  previousState(): void {
    window.history.back();
  }
}
