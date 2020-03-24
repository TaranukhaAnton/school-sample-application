import { IGradeLevel } from 'app/shared/model/grade-level.model';

export interface ISchollClass {
  id?: number;
  name?: string;
  gradeLevels?: IGradeLevel[];
}

export class SchollClass implements ISchollClass {
  constructor(public id?: number, public name?: string, public gradeLevels?: IGradeLevel[]) {}
}
