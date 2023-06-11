package models;

public class Workspace {

    private String id;
    private String name;
    private String type;
    private String visibility;
    private String description;

    public Workspace() {}

    public Workspace(String id) {
        setId(id);
    }

    // Used for GET requests
    public Workspace(String id, String name, String type, String visibility) {
        setId(id);
        setName(name);
        setType(type);
        setVisibility(visibility);
    }

    // Used for POST requests
    public Workspace(String name, String type, String description) {
        setName(name);
        setType(type);
        setDescription(description);
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
