import { BaseEntity } from './../../shared';

export const enum ReqTypeEnum {
    'RICORSOTRIBUTARIO',
    'RATEAZIONE'
}

export class Request implements BaseEntity {
    constructor(
        public id?: number,
        public subject?: string,
        public code?: string,
        public reqType?: ReqTypeEnum,
        public submissionDate?: any,
        public comments?: BaseEntity[],
        public docs?: BaseEntity[],
        public statusLogs?: BaseEntity[],
        public owner?: BaseEntity,
        public type?: BaseEntity,
        public status?: BaseEntity,
        public requestRicorsoTributario?: BaseEntity,
    ) {
    }
}
