import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { LegalTechAppPersonModule } from './person/person.module';
import { LegalTechAppRequestModule } from './request/request.module';
import { LegalTechAppRequestStatusLogModule } from './request-status-log/request-status-log.module';
import { LegalTechAppRequestTypeModule } from './request-type/request-type.module';
import { LegalTechAppDocStoreModule } from './doc-store/doc-store.module';
import { LegalTechAppRequestDocModule } from './request-doc/request-doc.module';
import { LegalTechAppRequestStatusModule } from './request-status/request-status.module';
import { LegalTechAppRequestRicorsoTributarioModule } from './request-ricorso-tributario/request-ricorso-tributario.module';
import { LegalTechAppRequestCommentModule } from './request-comment/request-comment.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        LegalTechAppPersonModule,
        LegalTechAppRequestModule,
        LegalTechAppRequestStatusLogModule,
        LegalTechAppRequestTypeModule,
        LegalTechAppDocStoreModule,
        LegalTechAppRequestDocModule,
        LegalTechAppRequestStatusModule,
        LegalTechAppRequestRicorsoTributarioModule,
        LegalTechAppRequestCommentModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LegalTechAppEntityModule {}
