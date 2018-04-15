import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Tour } from './tour.model';
import { TourService } from './tour.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-tour',
    templateUrl: './tour.component.html'
})
export class TourComponent implements OnInit, OnDestroy {
tours: Tour[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private tourService: TourService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.tourService.query().subscribe(
            (res: HttpResponse<Tour[]>) => {
                this.tours = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInTours();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Tour) {
        return item.id;
    }
    registerChangeInTours() {
        this.eventSubscriber = this.eventManager.subscribe('tourListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
