package com.nds.baking.king.net.tos;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Namrata Shah on 5/8/2017.
 */

public class Step {
    @SerializedName("id")
    private int stepID;
    @SerializedName("shortDescription")
    private String shortDescription;
    @SerializedName("description")
    private String description;
    @SerializedName("videoURL")
    private String videoURL;
    @SerializedName("thumbnailURL")
    private String thumbNailURL;

    public int getStepID() {
        return stepID;
    }

    public void setStepID(int stepID) {
        this.stepID = stepID;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getThumbNailURL() {
        return thumbNailURL;
    }

    public void setThumbNailURL(String thumbNailURL) {
        this.thumbNailURL = thumbNailURL;
    }

    @Override
    public String toString() {
        return "Step{" +
                "stepID=" + stepID +
                ", shortDescription='" + shortDescription + '\'' +
                ", description='" + description + '\'' +
                ", videoURL='" + videoURL + '\'' +
                ", thumbNailURL='" + thumbNailURL + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Step step = (Step) o;

        if (stepID != step.stepID) return false;
        if (!shortDescription.equals(step.shortDescription)) return false;
        if (!description.equals(step.description)) return false;
        if (!videoURL.equals(step.videoURL)) return false;
        return thumbNailURL.equals(step.thumbNailURL);
    }

    @Override
    public int hashCode() {
        int result = stepID;
        result = 31 * result + shortDescription.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + videoURL.hashCode();
        result = 31 * result + thumbNailURL.hashCode();
        return result;
    }
}
