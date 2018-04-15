import { BaseEntity } from './../../shared';

export class Reservation implements BaseEntity {
    constructor(
        public id?: number,
        public tourCode?: number,
        public personName?: string,
        public paid?: boolean,
        public notes?: string,
    ) {
        this.paid = false;
    }
}
