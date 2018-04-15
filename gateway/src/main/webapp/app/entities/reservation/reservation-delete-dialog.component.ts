import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Reservation } from './reservation.model';
import { ReservationPopupService } from './reservation-popup.service';
import { ReservationService } from './reservation.service';

@Component({
    selector: 'jhi-reservation-delete-dialog',
    templateUrl: './reservation-delete-dialog.component.html'
})
export class ReservationDeleteDialogComponent {

    reservation: Reservation;

    constructor(
        private reservationService: ReservationService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.reservationService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'reservationListModification',
                content: 'Deleted an reservation'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-reservation-delete-popup',
    template: ''
})
export class ReservationDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private reservationPopupService: ReservationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.reservationPopupService
                .open(ReservationDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
