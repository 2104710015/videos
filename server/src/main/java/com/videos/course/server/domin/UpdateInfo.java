package com.videos.course.server.domin;

import java.time.LocalDate;

public class UpdateInfo {
    private String id;

    private LocalDate updatetime;

    private String updatecontent;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(LocalDate updatetime) {
        this.updatetime = updatetime;
    }

    public String getUpdatecontent() {
        return updatecontent;
    }

    public void setUpdatecontent(String updatecontent) {
        this.updatecontent = updatecontent;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", updatetime=").append(updatetime);
        sb.append(", updatecontent=").append(updatecontent);
        sb.append("]");
        return sb.toString();
    }
}