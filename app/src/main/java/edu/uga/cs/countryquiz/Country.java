package edu.uga.cs.countryquiz;

/**
 * This class (a POJO) represents a single country, including the id, name,
 * and continent.
 * The id is -1 if the object has not been persisted in the database yet, and
 * the db table's primary key value, if it has been persisted.
 */
public class Country {
    private long id;
    private String name;
    private String continent;

    public Country() {
        this.id = -1;
        this.name = null;
        this.continent = null;
    }

    public Country(String name, String continent) {
        this.id = -1;  // the primary key id will be set by a setter method
        this.name = name;
        this.continent = continent;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }
}
