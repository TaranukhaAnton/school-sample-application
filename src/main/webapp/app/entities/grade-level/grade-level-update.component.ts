import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IGradeLevel, GradeLevel } from 'app/shared/model/grade-level.model';
import { GradeLevelService } from './grade-level.service';

@Component({
  selector: 'jhi-grade-level-update',
  templateUrl: './grade-level-update.component.html'
})
export class GradeLevelUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    value: []
  });

  constructor(protected gradeLevelService: GradeLevelService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ gradeLevel }) => {
      this.updateForm(gradeLevel);
    });
  }

  updateForm(gradeLevel: IGradeLevel): void {
    this.editForm.patchValue({
      id: gradeLevel.id,
      value: gradeLevel.value
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const gradeLevel = this.createFromForm();
    if (gradeLevel.id !== undefined) {
      this.subscribeToSaveResponse(this.gradeLevelService.update(gradeLevel));
    } else {
      this.subscribeToSaveResponse(this.gradeLevelService.create(gradeLevel));
    }
  }

  private createFromForm(): IGradeLevel {
    return {
      ...new GradeLevel(),
      id: this.editForm.get(['id'])!.value,
      value: this.editForm.get(['value'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGradeLevel>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
