package com.ppolivka.quittracker.dto;

import org.hibernate.validator.constraints.NotEmpty;

public class TrackerForm {

    @NotEmpty
    private String who;
    @NotEmpty
    private String when;
    @NotEmpty
    private String what;
    @NotEmpty
    private String url;

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public String getWhen() {
        return when;
    }

    public void setWhen(String when) {
        this.when = when;
    }

    public String getWhat() {
        return what;
    }

    public void setWhat(String what) {
        this.what = what;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
