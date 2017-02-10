package com.ppolivka.quittracker.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

@Entity
@Table(name = "tracker")
@SequenceGenerator(name="tracker_id_seq", sequenceName = "tracker_id_seq", allocationSize=1)
public class Tracker {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="tracker_id_seq")
    protected Integer id;

    @Column(name = "tracker_name")
    @NotEmpty
    protected String name;

    @Column(name = "tracker_slug")
    @NotEmpty
    protected String slug;

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

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }
}
