import { BaseEntity } from './../../shared';

export class Payment implements BaseEntity {
    constructor(
        public id?: number,
        public price?: number,
        public buyerName?: string,
        public notes?: string,
        public reservationCode?: BaseEntity,
    ) {
    }
}
