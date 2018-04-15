/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { HipsterTravelTestModule } from '../../../test.module';
import { ReservationDetailComponent } from '../../../../../../main/webapp/app/entities/reservation/reservation-detail.component';
import { ReservationService } from '../../../../../../main/webapp/app/entities/reservation/reservation.service';
import { Reservation } from '../../../../../../main/webapp/app/entities/reservation/reservation.model';

describe('Component Tests', () => {

    describe('Reservation Management Detail Component', () => {
        let comp: ReservationDetailComponent;
        let fixture: ComponentFixture<ReservationDetailComponent>;
        let service: ReservationService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HipsterTravelTestModule],
                declarations: [ReservationDetailComponent],
                providers: [
                    ReservationService
                ]
            })
            .overrideTemplate(ReservationDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ReservationDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ReservationService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Reservation(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.reservation).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
