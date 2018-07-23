package tech.ippon.chatbotdemo.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.BigDecimalFilter;





/**
 * Criteria class for the InsurancePlan entity. This class is used in InsurancePlanResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /insurance-plans?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class InsurancePlanCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter name;

    private StringFilter summary;

    private BigDecimalFilter yearlyPremium;

    private BigDecimalFilter deductable;

    private BigDecimalFilter coveragePerPerson;

    private BigDecimalFilter coveragePerAccident;

    public InsurancePlanCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getSummary() {
        return summary;
    }

    public void setSummary(StringFilter summary) {
        this.summary = summary;
    }

    public BigDecimalFilter getYearlyPremium() {
        return yearlyPremium;
    }

    public void setYearlyPremium(BigDecimalFilter yearlyPremium) {
        this.yearlyPremium = yearlyPremium;
    }

    public BigDecimalFilter getDeductable() {
        return deductable;
    }

    public void setDeductable(BigDecimalFilter deductable) {
        this.deductable = deductable;
    }

    public BigDecimalFilter getCoveragePerPerson() {
        return coveragePerPerson;
    }

    public void setCoveragePerPerson(BigDecimalFilter coveragePerPerson) {
        this.coveragePerPerson = coveragePerPerson;
    }

    public BigDecimalFilter getCoveragePerAccident() {
        return coveragePerAccident;
    }

    public void setCoveragePerAccident(BigDecimalFilter coveragePerAccident) {
        this.coveragePerAccident = coveragePerAccident;
    }

    @Override
    public String toString() {
        return "InsurancePlanCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (summary != null ? "summary=" + summary + ", " : "") +
                (yearlyPremium != null ? "yearlyPremium=" + yearlyPremium + ", " : "") +
                (deductable != null ? "deductable=" + deductable + ", " : "") +
                (coveragePerPerson != null ? "coveragePerPerson=" + coveragePerPerson + ", " : "") +
                (coveragePerAccident != null ? "coveragePerAccident=" + coveragePerAccident + ", " : "") +
            "}";
    }

}
