package ava.coding.challenge.main.organization.access.rights.entities;

public class QuantityRestriction {

    private int restrictedAmount;
    private RestrictingCondition restrictingCondition;

    public QuantityRestriction(){}
    public QuantityRestriction(int restrictedAmount, RestrictingCondition restrictingCondition) {
        this.restrictedAmount = restrictedAmount;
        this.restrictingCondition = restrictingCondition;
    }

    public int getRestrictedAmount() {
        return restrictedAmount;
    }

    public void setRestrictedAmount(int restrictedAmount) {
        this.restrictedAmount = restrictedAmount;
    }

    public RestrictingCondition getRestrictingCondition() {
        return restrictingCondition;
    }

    public void setRestrictingCondition(RestrictingCondition restrictingCondition) {
        this.restrictingCondition = restrictingCondition;
    }
}
