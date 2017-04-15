package io.mdevlab.ocatestapp.model.question;

import io.realm.RealmObject;

/**
 * Created by husaynhakeem on 4/16/17.
 */

public class BaseQuestion extends RealmObject {

    private int id;
    /*
    Either single answer question or multi answer question
    Both are constants and are defined in the util.Constants class
     */
    private int type;
    private String explanation;
    private boolean isFavorite;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
