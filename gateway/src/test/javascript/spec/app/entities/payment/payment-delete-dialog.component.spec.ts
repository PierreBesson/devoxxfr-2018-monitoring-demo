/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { HipsterTravelTestModule } from '../../../test.module';
import { PaymentDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/payment/payment-delete-dialog.component';
import { PaymentService } from '../../../../../../main/webapp/app/entities/payment/payment.service';

describe('Component Tests', () => {

    describe('Payment Management Delete Component', () => {
        let comp: PaymentDeleteDialogComponent;
        let fixture: ComponentFixture<PaymentDeleteDialogComponent>;
        let service: PaymentService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HipsterTravelTestModule],
                declarations: [PaymentDeleteDialogComponent],
                providers: [
                    PaymentService
                ]
            })
            .overrideTemplate(PaymentDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PaymentDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PaymentService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
