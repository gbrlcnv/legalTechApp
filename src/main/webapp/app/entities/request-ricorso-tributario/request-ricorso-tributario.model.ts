import { BaseEntity } from './../../shared';

export const enum TipologiaAttoEnum {
    'VERBALEACCERTAMENTO',
    'VERBALECOSTATAZIONE',
    'CARTELLAESATTORIALE',
    'PREAVVISOIPOTECA'
}

export const enum ReqTypeEnum {
    'RICORSOTRIBUTARIO',
    'RATEAZIONE'
}

export class RequestRicorsoTributario implements BaseEntity {
    constructor(
        public id?: number,
        public notificaDate?: any,
        public emissioneRuoloDate?: any,
        public tipologiaAtto?: TipologiaAttoEnum,
        public reqType?: ReqTypeEnum,
        public requests?: BaseEntity[],
    ) {
    }
}
