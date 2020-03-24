import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISchollClass } from 'app/shared/model/scholl-class.model';

type EntityResponseType = HttpResponse<ISchollClass>;
type EntityArrayResponseType = HttpResponse<ISchollClass[]>;

@Injectable({ providedIn: 'root' })
export class SchollClassService {
  public resourceUrl = SERVER_API_URL + 'api/scholl-classes';

  constructor(protected http: HttpClient) {}

  create(schollClass: ISchollClass): Observable<EntityResponseType> {
    return this.http.post<ISchollClass>(this.resourceUrl, schollClass, { observe: 'response' });
  }

  update(schollClass: ISchollClass): Observable<EntityResponseType> {
    return this.http.put<ISchollClass>(this.resourceUrl, schollClass, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISchollClass>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISchollClass[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
