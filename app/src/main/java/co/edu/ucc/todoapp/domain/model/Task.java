package co.edu.ucc.todoapp.domain.model;

/**
 * Created by jggomez on 23-Mar-18.
 */

public class Task {

    private String id;
    private String name;
    private String uriImage;

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

    public String getUriImage() {
        return uriImage;
    }

    public void setUriImage(String uriImage) {
        this.uriImage = uriImage;
    }
}
