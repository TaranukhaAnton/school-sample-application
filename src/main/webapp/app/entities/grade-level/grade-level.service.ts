import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IGradeLevel } from 'app/shared/model/grade-level.model';

type EntityResponseType = HttpResponse<IGradeLevel>;
type EntityArrayResponseType = HttpResponse<IGradeLevel[]>;

@Injectable({ providedIn: 'root' })
export class GradeLevelService {
  public resourceUrl = SERVER_API_URL + 'api/grade-levels';

  constructor(protected http: HttpClient) {}

  create(gradeLevel: IGradeLevel): Observable<EntityResponseType> {
    return this.http.post<IGradeLevel>(this.resourceUrl, gradeLevel, { observe: 'response' });
  }

  update(gradeLevel: IGradeLevel): Observable<EntityResponseType> {
    return this.http.put<IGradeLevel>(this.resourceUrl, gradeLevel, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IGradeLevel>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGradeLevel[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
