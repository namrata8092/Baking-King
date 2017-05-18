package com.nds.baking.king.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.nds.baking.king.utils.ValidationUtil;

/**
 * Created by Namrata Shah on 5/9/2017.
 */

public class RecipeStepModel implements Parcelable {

    private String id;
    private String shortDescription;
    private String description;
    private String videoURL;
    private String thumbNailURL;

    public RecipeStepModel(String id, String shortDesc, String desc){
        this.id = id;
        this.shortDescription = shortDesc;
        this.description = desc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public boolean isWithVideoURL() {
        return ValidationUtil.isValidString(this.videoURL);
    }

    public boolean isWithThumbNailURL() {
        return ValidationUtil.isValidString(this.thumbNailURL);
    }

    protected RecipeStepModel(Parcel in) {
        id = in.readString();
        shortDescription = in.readString();
        description = in.readString();
        videoURL = in.readString();
        thumbNailURL = in.readString();
    }

    public static final Creator<RecipeStepModel> CREATOR = new Creator<RecipeStepModel>() {
        @Override
        public RecipeStepModel createFromParcel(Parcel in) {
            return new RecipeStepModel(in);
        }

        @Override
        public RecipeStepModel[] newArray(int size) {
            return new RecipeStepModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(shortDescription);
        dest.writeString(description);
        dest.writeString(videoURL);
        dest.writeString(thumbNailURL);
    }
}
