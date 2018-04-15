/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { HipsterTravelTestModule } from '../../../test.module';
import { TourDetailComponent } from '../../../../../../main/webapp/app/entities/tour/tour-detail.component';
import { TourService } from '../../../../../../main/webapp/app/entities/tour/tour.service';
import { Tour } from '../../../../../../main/webapp/app/entities/tour/tour.model';

describe('Component Tests', () => {

    describe('Tour Management Detail Component', () => {
        let comp: TourDetailComponent;
        let fixture: ComponentFixture<TourDetailComponent>;
        let service: TourService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HipsterTravelTestModule],
                declarations: [TourDetailComponent],
                providers: [
                    TourService
                ]
            })
            .overrideTemplate(TourDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TourDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TourService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Tour(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.tour).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
