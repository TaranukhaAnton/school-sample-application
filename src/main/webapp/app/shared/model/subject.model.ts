import { IGradeLevel } from 'app/shared/model/grade-level.model';
import { IGroup } from 'app/shared/model/group.model';

export interface ISubject {
  id?: number;
  name?: string;
  common?: boolean;
  gradeLevels?: IGradeLevel[];
  groups?: IGroup[];
}

export class Subject implements ISubject {
  constructor(
    public id?: number,
    public name?: string,
    public common?: boolean,
    public gradeLevels?: IGradeLevel[],
    public groups?: IGroup[]
  ) {
    this.common = this.common || false;
  }
}
