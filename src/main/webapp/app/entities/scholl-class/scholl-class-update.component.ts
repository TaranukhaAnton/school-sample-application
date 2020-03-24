import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISchollClass, SchollClass } from 'app/shared/model/scholl-class.model';
import { SchollClassService } from './scholl-class.service';
import { IGradeLevel } from 'app/shared/model/grade-level.model';
import { GradeLevelService } from 'app/entities/grade-level/grade-level.service';

@Component({
  selector: 'jhi-scholl-class-update',
  templateUrl: './scholl-class-update.component.html'
})
export class SchollClassUpdateComponent implements OnInit {
  isSaving = false;
  gradelevels: IGradeLevel[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    gradeLevels: []
  });

  constructor(
    protected schollClassService: SchollClassService,
    protected gradeLevelService: GradeLevelService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ schollClass }) => {
      this.updateForm(schollClass);

      this.gradeLevelService.query().subscribe((res: HttpResponse<IGradeLevel[]>) => (this.gradelevels = res.body || []));
    });
  }

  updateForm(schollClass: ISchollClass): void {
    this.editForm.patchValue({
      id: schollClass.id,
      name: schollClass.name,
      gradeLevels: schollClass.gradeLevels
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const schollClass = this.createFromForm();
    if (schollClass.id !== undefined) {
      this.subscribeToSaveResponse(this.schollClassService.update(schollClass));
    } else {
      this.subscribeToSaveResponse(this.schollClassService.create(schollClass));
    }
  }

  private createFromForm(): ISchollClass {
    return {
      ...new SchollClass(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      gradeLevels: this.editForm.get(['gradeLevels'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISchollClass>>): void {
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

  trackById(index: number, item: IGradeLevel): any {
    return item.id;
  }

  getSelected(selectedVals: IGradeLevel[], option: IGradeLevel): IGradeLevel {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
