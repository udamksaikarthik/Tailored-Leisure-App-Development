package com.project.tailoredleisureappdevelopment.entities;

public class Need {
    private int need_id;
    private String need_short_desc;
    private String need_long_desc;

    public Need(){

    }

    public int getNeed_id() {
        return need_id;
    }

    public void setNeed_id(int need_id) {
        this.need_id = need_id;
    }

    public String getNeed_short_desc() {
        return need_short_desc;
    }

    public void setNeed_short_desc(String need_short_desc) {
        this.need_short_desc = need_short_desc;
    }

    public String getNeed_long_desc() {
        return need_long_desc;
    }

    public void setNeed_long_desc(String need_long_desc) {
        this.need_long_desc = need_long_desc;
    }
}
