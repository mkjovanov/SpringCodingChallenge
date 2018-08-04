package access.rights.rest.api.organization;

import access.rights.rest.api.repository.IEntity;

//@Entity
public class Organization implements IEntity {

    //@Id
    private Integer id;
    private String name;

    public Organization() { }
    public Organization(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
