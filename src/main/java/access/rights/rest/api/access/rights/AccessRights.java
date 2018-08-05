package access.rights.rest.api.access.rights;

public class AccessRights {

    private boolean create;
    private boolean read;
    private boolean update;
    private boolean delete;
    private QuantityRestriction quantityRestriction;

    public AccessRights() {
        this(false, true, false, false, null);
    }
    public AccessRights(boolean create, boolean read, boolean update, boolean delete) {
        this(create, read, update, delete, null);
    }
    public AccessRights(boolean create, boolean read, boolean update, boolean delete, QuantityRestriction quantityRestriction) {
        this.create = create;
        this.read = read;
        this.update = update;
        this.delete = delete;
        this.quantityRestriction = quantityRestriction;
    }

    public boolean isCreate() {
        return create;
    }

    public void setCreate(boolean create) {
        this.create = create;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public QuantityRestriction getQuantityRestriction() {
        return quantityRestriction;
    }

    public void setQuantityRestriction(QuantityRestriction quantityRestriction) {
        this.quantityRestriction = quantityRestriction;
    }
}
