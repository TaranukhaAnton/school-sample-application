import { IGradeLevel } from 'app/shared/model/grade-level.model';
import { ISchollClass } from 'app/shared/model/scholl-class.model';
import { IGroup } from 'app/shared/model/group.model';

export interface IStudent {
  id?: number;
  firstName?: string;
  lastName?: string;
  gradeLevel?: IGradeLevel;
  schoolClass?: ISchollClass;
  groups?: IGroup[];
}

export class Student implements IStudent {
  constructor(
    public id?: number,
    public firstName?: string,
    public lastName?: string,
    public gradeLevel?: IGradeLevel,
    public schoolClass?: ISchollClass,
    public groups?: IGroup[]
  ) {}
}
