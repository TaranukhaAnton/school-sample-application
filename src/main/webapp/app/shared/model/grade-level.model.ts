import { ISchollClass } from 'app/shared/model/scholl-class.model';
import { ISubject } from 'app/shared/model/subject.model';

export interface IGradeLevel {
  id?: number;
  value?: number;
  schoolClasses?: ISchollClass[];
  subjects?: ISubject[];
}

export class GradeLevel implements IGradeLevel {
  constructor(public id?: number, public value?: number, public schoolClasses?: ISchollClass[], public subjects?: ISubject[]) {}
}
