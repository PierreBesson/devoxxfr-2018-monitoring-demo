import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { HipsterTravelTourModule } from './tour/tour.module';
import { HipsterTravelReservationModule } from './reservation/reservation.module';
import { HipsterTravelPaymentModule } from './payment/payment.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        HipsterTravelTourModule,
        HipsterTravelReservationModule,
        HipsterTravelPaymentModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HipsterTravelEntityModule {}
