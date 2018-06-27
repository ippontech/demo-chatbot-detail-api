package org.jhipster.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import org.jhipster.domain.enumeration.InsuranceType;

/**
 * A InsuranceDetails.
 */
@Entity
@Table(name = "insurance_details")
public class InsuranceDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_level")
    private InsuranceType level;

    @Column(name = "annual_premium", precision=10, scale=2)
    private BigDecimal annualPremium;

    @Column(name = "injury_liability")
    private Boolean injuryLiability;

    @Column(name = "property_liability")
    private Boolean propertyLiability;

    @Column(name = "uninsured_motorist_injury")
    private Boolean uninsuredMotoristInjury;

    @Column(name = "uninsured_motorist_property")
    private Boolean uninsuredMotoristProperty;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public InsuranceType getLevel() {
        return level;
    }

    public InsuranceDetails level(InsuranceType level) {
        this.level = level;
        return this;
    }

    public void setLevel(InsuranceType level) {
        this.level = level;
    }

    public BigDecimal getAnnualPremium() {
        return annualPremium;
    }

    public InsuranceDetails annualPremium(BigDecimal annualPremium) {
        this.annualPremium = annualPremium;
        return this;
    }

    public void setAnnualPremium(BigDecimal annualPremium) {
        this.annualPremium = annualPremium;
    }

    public Boolean isInjuryLiability() {
        return injuryLiability;
    }

    public InsuranceDetails injuryLiability(Boolean injuryLiability) {
        this.injuryLiability = injuryLiability;
        return this;
    }

    public void setInjuryLiability(Boolean injuryLiability) {
        this.injuryLiability = injuryLiability;
    }

    public Boolean isPropertyLiability() {
        return propertyLiability;
    }

    public InsuranceDetails propertyLiability(Boolean propertyLiability) {
        this.propertyLiability = propertyLiability;
        return this;
    }

    public void setPropertyLiability(Boolean propertyLiability) {
        this.propertyLiability = propertyLiability;
    }

    public Boolean isUninsuredMotoristInjury() {
        return uninsuredMotoristInjury;
    }

    public InsuranceDetails uninsuredMotoristInjury(Boolean uninsuredMotoristInjury) {
        this.uninsuredMotoristInjury = uninsuredMotoristInjury;
        return this;
    }

    public void setUninsuredMotoristInjury(Boolean uninsuredMotoristInjury) {
        this.uninsuredMotoristInjury = uninsuredMotoristInjury;
    }

    public Boolean isUninsuredMotoristProperty() {
        return uninsuredMotoristProperty;
    }

    public InsuranceDetails uninsuredMotoristProperty(Boolean uninsuredMotoristProperty) {
        this.uninsuredMotoristProperty = uninsuredMotoristProperty;
        return this;
    }

    public void setUninsuredMotoristProperty(Boolean uninsuredMotoristProperty) {
        this.uninsuredMotoristProperty = uninsuredMotoristProperty;
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
        InsuranceDetails insuranceDetails = (InsuranceDetails) o;
        if (insuranceDetails.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), insuranceDetails.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InsuranceDetails{" +
            "id=" + getId() +
            ", level='" + getLevel() + "'" +
            ", annualPremium=" + getAnnualPremium() +
            ", injuryLiability='" + isInjuryLiability() + "'" +
            ", propertyLiability='" + isPropertyLiability() + "'" +
            ", uninsuredMotoristInjury='" + isUninsuredMotoristInjury() + "'" +
            ", uninsuredMotoristProperty='" + isUninsuredMotoristProperty() + "'" +
            "}";
    }
}
