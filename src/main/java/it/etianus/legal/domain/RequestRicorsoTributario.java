package it.etianus.legal.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import it.etianus.legal.domain.enumeration.TipologiaAttoEnum;

import it.etianus.legal.domain.enumeration.ReqTypeEnum;

/**
 * A RequestRicorsoTributario.
 */
@Entity
@Table(name = "request_ricorso_tributario")
public class RequestRicorsoTributario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "notifica_date", nullable = false)
    private ZonedDateTime notificaDate;

    /**
     * nome o ragione sociale
     */
    @NotNull
    @ApiModelProperty(value = "nome o ragione sociale", required = true)
    @Column(name = "emissione_ruolo_date", nullable = false)
    private ZonedDateTime emissioneRuoloDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipologia_atto")
    private TipologiaAttoEnum tipologiaAtto;

    /**
     * tipo di documento utilizzato per l'identificazione
     */
    @ApiModelProperty(value = "tipo di documento utilizzato per l'identificazione")
    @Enumerated(EnumType.STRING)
    @Column(name = "req_type")
    private ReqTypeEnum reqType;

    @OneToMany(mappedBy = "requestRicorsoTributario")
    @JsonIgnore
    private Set<Request> requests = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getNotificaDate() {
        return notificaDate;
    }

    public RequestRicorsoTributario notificaDate(ZonedDateTime notificaDate) {
        this.notificaDate = notificaDate;
        return this;
    }

    public void setNotificaDate(ZonedDateTime notificaDate) {
        this.notificaDate = notificaDate;
    }

    public ZonedDateTime getEmissioneRuoloDate() {
        return emissioneRuoloDate;
    }

    public RequestRicorsoTributario emissioneRuoloDate(ZonedDateTime emissioneRuoloDate) {
        this.emissioneRuoloDate = emissioneRuoloDate;
        return this;
    }

    public void setEmissioneRuoloDate(ZonedDateTime emissioneRuoloDate) {
        this.emissioneRuoloDate = emissioneRuoloDate;
    }

    public TipologiaAttoEnum getTipologiaAtto() {
        return tipologiaAtto;
    }

    public RequestRicorsoTributario tipologiaAtto(TipologiaAttoEnum tipologiaAtto) {
        this.tipologiaAtto = tipologiaAtto;
        return this;
    }

    public void setTipologiaAtto(TipologiaAttoEnum tipologiaAtto) {
        this.tipologiaAtto = tipologiaAtto;
    }

    public ReqTypeEnum getReqType() {
        return reqType;
    }

    public RequestRicorsoTributario reqType(ReqTypeEnum reqType) {
        this.reqType = reqType;
        return this;
    }

    public void setReqType(ReqTypeEnum reqType) {
        this.reqType = reqType;
    }

    public Set<Request> getRequests() {
        return requests;
    }

    public RequestRicorsoTributario requests(Set<Request> requests) {
        this.requests = requests;
        return this;
    }

    public RequestRicorsoTributario addRequest(Request request) {
        this.requests.add(request);
        request.setRequestRicorsoTributario(this);
        return this;
    }

    public RequestRicorsoTributario removeRequest(Request request) {
        this.requests.remove(request);
        request.setRequestRicorsoTributario(null);
        return this;
    }

    public void setRequests(Set<Request> requests) {
        this.requests = requests;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RequestRicorsoTributario requestRicorsoTributario = (RequestRicorsoTributario) o;
        if (requestRicorsoTributario.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), requestRicorsoTributario.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RequestRicorsoTributario{" +
            "id=" + getId() +
            ", notificaDate='" + getNotificaDate() + "'" +
            ", emissioneRuoloDate='" + getEmissioneRuoloDate() + "'" +
            ", tipologiaAtto='" + getTipologiaAtto() + "'" +
            ", reqType='" + getReqType() + "'" +
            "}";
    }
}
