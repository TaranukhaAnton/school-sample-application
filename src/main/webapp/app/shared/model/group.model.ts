import { IGradeLevel } from 'app/shared/model/grade-level.model';
import { ISubject } from 'app/shared/model/subject.model';
import { IStudent } from 'app/shared/model/student.model';

export interface IGroup {
  id?: number;
  name?: string;
  gradeLevel?: IGradeLevel;
  subjects?: ISubject[];
  students?: IStudent[];
}

export class Group implements IGroup {
  constructor(
    public id?: number,
    public name?: string,
    public gradeLevel?: IGradeLevel,
    public subjects?: ISubject[],
    public students?: IStudent[]
  ) {}
}
