import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LegalTechAppSharedModule } from '../../shared';
import {
    RequestCommentService,
    RequestCommentPopupService,
    RequestCommentComponent,
    RequestCommentDetailComponent,
    RequestCommentDialogComponent,
    RequestCommentPopupComponent,
    RequestCommentDeletePopupComponent,
    RequestCommentDeleteDialogComponent,
    requestCommentRoute,
    requestCommentPopupRoute,
} from './';

const ENTITY_STATES = [
    ...requestCommentRoute,
    ...requestCommentPopupRoute,
];

@NgModule({
    imports: [
        LegalTechAppSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        RequestCommentComponent,
        RequestCommentDetailComponent,
        RequestCommentDialogComponent,
        RequestCommentDeleteDialogComponent,
        RequestCommentPopupComponent,
        RequestCommentDeletePopupComponent,
    ],
    entryComponents: [
        RequestCommentComponent,
        RequestCommentDialogComponent,
        RequestCommentPopupComponent,
        RequestCommentDeleteDialogComponent,
        RequestCommentDeletePopupComponent,
    ],
    providers: [
        RequestCommentService,
        RequestCommentPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LegalTechAppRequestCommentModule {}
