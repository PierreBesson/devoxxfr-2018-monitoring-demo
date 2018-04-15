import { BaseEntity } from './../../shared';

export class Tour implements BaseEntity {
    constructor(
        public id?: number,
        public tourName?: string,
        public type?: string,
        public duration?: string,
        public fromDate?: any,
        public toDate?: any,
        public description?: string,
    ) {
    }
}
