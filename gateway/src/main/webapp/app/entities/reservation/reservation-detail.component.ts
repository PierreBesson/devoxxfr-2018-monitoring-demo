import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Reservation } from './reservation.model';
import { ReservationService } from './reservation.service';

@Component({
    selector: 'jhi-reservation-detail',
    templateUrl: './reservation-detail.component.html'
})
export class ReservationDetailComponent implements OnInit, OnDestroy {

    reservation: Reservation;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private reservationService: ReservationService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInReservations();
    }

    load(id) {
        this.reservationService.find(id)
            .subscribe((reservationResponse: HttpResponse<Reservation>) => {
                this.reservation = reservationResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInReservations() {
        this.eventSubscriber = this.eventManager.subscribe(
            'reservationListModification',
            (response) => this.load(this.reservation.id)
        );
    }
}
