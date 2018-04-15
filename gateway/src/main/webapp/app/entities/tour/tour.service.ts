import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Tour } from './tour.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Tour>;

@Injectable()
export class TourService {

    private resourceUrl =  SERVER_API_URL + 'catalog/api/tours';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(tour: Tour): Observable<EntityResponseType> {
        const copy = this.convert(tour);
        return this.http.post<Tour>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(tour: Tour): Observable<EntityResponseType> {
        const copy = this.convert(tour);
        return this.http.put<Tour>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Tour>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Tour[]>> {
        const options = createRequestOption(req);
        return this.http.get<Tour[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Tour[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Tour = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Tour[]>): HttpResponse<Tour[]> {
        const jsonResponse: Tour[] = res.body;
        const body: Tour[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Tour.
     */
    private convertItemFromServer(tour: Tour): Tour {
        const copy: Tour = Object.assign({}, tour);
        copy.fromDate = this.dateUtils
            .convertDateTimeFromServer(tour.fromDate);
        copy.toDate = this.dateUtils
            .convertDateTimeFromServer(tour.toDate);
        return copy;
    }

    /**
     * Convert a Tour to a JSON which can be sent to the server.
     */
    private convert(tour: Tour): Tour {
        const copy: Tour = Object.assign({}, tour);

        copy.fromDate = this.dateUtils.toDate(tour.fromDate);

        copy.toDate = this.dateUtils.toDate(tour.toDate);
        return copy;
    }
}
