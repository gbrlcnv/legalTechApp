import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LegalTechAppSharedModule } from '../../shared';
import {
    DocStoreService,
    DocStorePopupService,
    DocStoreComponent,
    DocStoreDetailComponent,
    DocStoreDialogComponent,
    DocStorePopupComponent,
    DocStoreDeletePopupComponent,
    DocStoreDeleteDialogComponent,
    docStoreRoute,
    docStorePopupRoute,
} from './';

const ENTITY_STATES = [
    ...docStoreRoute,
    ...docStorePopupRoute,
];

@NgModule({
    imports: [
        LegalTechAppSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        DocStoreComponent,
        DocStoreDetailComponent,
        DocStoreDialogComponent,
        DocStoreDeleteDialogComponent,
        DocStorePopupComponent,
        DocStoreDeletePopupComponent,
    ],
    entryComponents: [
        DocStoreComponent,
        DocStoreDialogComponent,
        DocStorePopupComponent,
        DocStoreDeleteDialogComponent,
        DocStoreDeletePopupComponent,
    ],
    providers: [
        DocStoreService,
        DocStorePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LegalTechAppDocStoreModule {}
