package tech.ippon.chatbotdemo.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A InsurancePlan.
 */
@Entity
@Table(name = "insurance_plan")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class InsurancePlan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "summary")
    private String summary;

    @Column(name = "yearly_premium", precision = 10, scale = 2)
    private BigDecimal yearlyPremium;

    @Column(name = "deductable", precision = 10, scale = 2)
    private BigDecimal deductable;

    @Column(name = "coverage_per_person", precision = 10, scale = 2)
    private BigDecimal coveragePerPerson;

    @Column(name = "coverage_per_accident", precision = 10, scale = 2)
    private BigDecimal coveragePerAccident;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public InsurancePlan name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSummary() {
        return summary;
    }

    public InsurancePlan summary(String summary) {
        this.summary = summary;
        return this;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public BigDecimal getYearlyPremium() {
        return yearlyPremium;
    }

    public InsurancePlan yearlyPremium(BigDecimal yearlyPremium) {
        this.yearlyPremium = yearlyPremium;
        return this;
    }

    public void setYearlyPremium(BigDecimal yearlyPremium) {
        this.yearlyPremium = yearlyPremium;
    }

    public BigDecimal getDeductable() {
        return deductable;
    }

    public InsurancePlan deductable(BigDecimal deductable) {
        this.deductable = deductable;
        return this;
    }

    public void setDeductable(BigDecimal deductable) {
        this.deductable = deductable;
    }

    public BigDecimal getCoveragePerPerson() {
        return coveragePerPerson;
    }

    public InsurancePlan coveragePerPerson(BigDecimal coveragePerPerson) {
        this.coveragePerPerson = coveragePerPerson;
        return this;
    }

    public void setCoveragePerPerson(BigDecimal coveragePerPerson) {
        this.coveragePerPerson = coveragePerPerson;
    }

    public BigDecimal getCoveragePerAccident() {
        return coveragePerAccident;
    }

    public InsurancePlan coveragePerAccident(BigDecimal coveragePerAccident) {
        this.coveragePerAccident = coveragePerAccident;
        return this;
    }

    public void setCoveragePerAccident(BigDecimal coveragePerAccident) {
        this.coveragePerAccident = coveragePerAccident;
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
        InsurancePlan insurancePlan = (InsurancePlan) o;
        if (insurancePlan.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), insurancePlan.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InsurancePlan{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", summary='" + getSummary() + "'" +
            ", yearlyPremium=" + getYearlyPremium() +
            ", deductable=" + getDeductable() +
            ", coveragePerPerson=" + getCoveragePerPerson() +
            ", coveragePerAccident=" + getCoveragePerAccident() +
            "}";
    }
}
