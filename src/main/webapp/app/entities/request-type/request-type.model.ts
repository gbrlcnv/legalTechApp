import { BaseEntity } from './../../shared';

export const enum RequestStatusEnum {
    'PROPOSED',
    'OPEN',
    'CLOSED',
    'REFUSED',
    'APPROVED',
    'DELETED',
    'SUCCESS'
}

export class RequestType implements BaseEntity {
    constructor(
        public id?: number,
        public reqStatus?: RequestStatusEnum,
        public code?: string,
        public requests?: BaseEntity[],
    ) {
    }
}
