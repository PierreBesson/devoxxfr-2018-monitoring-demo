import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Tour } from './tour.model';
import { TourService } from './tour.service';

@Component({
    selector: 'jhi-tour-detail',
    templateUrl: './tour-detail.component.html'
})
export class TourDetailComponent implements OnInit, OnDestroy {

    tour: Tour;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private tourService: TourService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTours();
    }

    load(id) {
        this.tourService.find(id)
            .subscribe((tourResponse: HttpResponse<Tour>) => {
                this.tour = tourResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTours() {
        this.eventSubscriber = this.eventManager.subscribe(
            'tourListModification',
            (response) => this.load(this.tour.id)
        );
    }
}
