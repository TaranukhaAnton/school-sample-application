import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IStudent, Student } from 'app/shared/model/student.model';
import { StudentService } from './student.service';
import { IGradeLevel } from 'app/shared/model/grade-level.model';
import { GradeLevelService } from 'app/entities/grade-level/grade-level.service';
import { ISchollClass } from 'app/shared/model/scholl-class.model';
import { SchollClassService } from 'app/entities/scholl-class/scholl-class.service';

type SelectableEntity = IGradeLevel | ISchollClass;

@Component({
  selector: 'jhi-student-update',
  templateUrl: './student-update.component.html'
})
export class StudentUpdateComponent implements OnInit {
  isSaving = false;
  gradelevels: IGradeLevel[] = [];
  schollclasses: ISchollClass[] = [];

  editForm = this.fb.group({
    id: [],
    firstName: [],
    lastName: [],
    gradeLevel: [],
    schoolClass: []
  });

  constructor(
    protected studentService: StudentService,
    protected gradeLevelService: GradeLevelService,
    protected schollClassService: SchollClassService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ student }) => {
      this.updateForm(student);

      this.gradeLevelService.query().subscribe((res: HttpResponse<IGradeLevel[]>) => (this.gradelevels = res.body || []));

      this.schollClassService.query().subscribe((res: HttpResponse<ISchollClass[]>) => (this.schollclasses = res.body || []));
    });
  }

  updateForm(student: IStudent): void {
    this.editForm.patchValue({
      id: student.id,
      firstName: student.firstName,
      lastName: student.lastName,
      gradeLevel: student.gradeLevel,
      schoolClass: student.schoolClass
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const student = this.createFromForm();
    if (student.id !== undefined) {
      this.subscribeToSaveResponse(this.studentService.update(student));
    } else {
      this.subscribeToSaveResponse(this.studentService.create(student));
    }
  }

  private createFromForm(): IStudent {
    return {
      ...new Student(),
      id: this.editForm.get(['id'])!.value,
      firstName: this.editForm.get(['firstName'])!.value,
      lastName: this.editForm.get(['lastName'])!.value,
      gradeLevel: this.editForm.get(['gradeLevel'])!.value,
      schoolClass: this.editForm.get(['schoolClass'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStudent>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
