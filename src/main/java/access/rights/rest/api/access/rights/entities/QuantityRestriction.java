package access.rights.rest.api.access.rights.entities;

public class QuantityRestriction {

    private int restrictedAmmount;
    private RestrictingCondition restrictingCondition;

    public QuantityRestriction(){}
    public QuantityRestriction(int restrictedAmmount, RestrictingCondition restrictingCondition) {
        this.restrictedAmmount = restrictedAmmount;
        this.restrictingCondition = restrictingCondition;
    }

    public int getRestrictedAmmount() {
        return restrictedAmmount;
    }

    public void setRestrictedAmmount(int restrictedAmmount) {
        this.restrictedAmmount = restrictedAmmount;
    }

    public RestrictingCondition getRestrictingCondition() {
        return restrictingCondition;
    }

    public void setRestrictingCondition(RestrictingCondition restrictingCondition) {
        this.restrictingCondition = restrictingCondition;
    }
}
