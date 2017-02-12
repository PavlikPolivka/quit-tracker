package com.ppolivka.quittracker.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

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

    @Column(name = "tracker_item")
    @NotEmpty
    protected String item;

    @Column(name = "tracker_stop_date")
    @NotNull
    protected Date stopDate;

    @Column(name = "tracker_created_date")
    @NotNull
    protected Date createdDate;

    @Column(name = "tracker_owner")
    @NotEmpty
    protected String owner;

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

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Date getStopDate() {
        return stopDate;
    }

    public void setStopDate(Date stopDate) {
        this.stopDate = stopDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
