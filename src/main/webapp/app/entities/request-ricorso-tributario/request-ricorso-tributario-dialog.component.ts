import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RequestRicorsoTributario } from './request-ricorso-tributario.model';
import { RequestRicorsoTributarioPopupService } from './request-ricorso-tributario-popup.service';
import { RequestRicorsoTributarioService } from './request-ricorso-tributario.service';

@Component({
    selector: 'jhi-request-ricorso-tributario-dialog',
    templateUrl: './request-ricorso-tributario-dialog.component.html'
})
export class RequestRicorsoTributarioDialogComponent implements OnInit {

    requestRicorsoTributario: RequestRicorsoTributario;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private requestRicorsoTributarioService: RequestRicorsoTributarioService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.requestRicorsoTributario.id !== undefined) {
            this.subscribeToSaveResponse(
                this.requestRicorsoTributarioService.update(this.requestRicorsoTributario));
        } else {
            this.subscribeToSaveResponse(
                this.requestRicorsoTributarioService.create(this.requestRicorsoTributario));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<RequestRicorsoTributario>>) {
        result.subscribe((res: HttpResponse<RequestRicorsoTributario>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: RequestRicorsoTributario) {
        this.eventManager.broadcast({ name: 'requestRicorsoTributarioListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-request-ricorso-tributario-popup',
    template: ''
})
export class RequestRicorsoTributarioPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private requestRicorsoTributarioPopupService: RequestRicorsoTributarioPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.requestRicorsoTributarioPopupService
                    .open(RequestRicorsoTributarioDialogComponent as Component, params['id']);
            } else {
                this.requestRicorsoTributarioPopupService
                    .open(RequestRicorsoTributarioDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
