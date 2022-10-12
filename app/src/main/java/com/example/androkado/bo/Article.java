package com.example.androkado.bo;


import android.os.Parcel;
import android.os.Parcelable;

public class Article implements Parcelable {
    private int id;
    private String nom;
    private Double prix;
    private String description;
    private float note;
    private String url;
    private Boolean etat;

    public Article(){

    }

    public Article(int id, String nom, Double prix, String description, float note, String url, Boolean etat) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.description = description;
        this.note = note;
        this.url = url;
        this.etat = etat;
    }

    public Article(String nom, Double prix, String description, float note, String url, Boolean etat) {
        this.nom = nom;
        this.prix = prix;
        this.description = description;
        this.note = note;
        this.url = url;
        this.etat = etat;
    }


    protected Article(Parcel in) {
        id = in.readInt();
        nom = in.readString();
        if (in.readByte() == 0) {
            prix = null;
        } else {
            prix = in.readDouble();
        }
        description = in.readString();
        note = in.readFloat();
        url = in.readString();
        byte tmpEtat = in.readByte();
        etat = tmpEtat == 0 ? null : tmpEtat == 1;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nom);
        if (prix == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(prix);
        }
        dest.writeString(description);
        dest.writeFloat(note);
        dest.writeString(url);
        dest.writeByte((byte) (etat == null ? 0 : etat ? 1 : 2));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getNote() {
        return note;
    }

    public void setNote(float note) {
        this.note = note;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getEtat() {
        return etat;
    }

    public void setEtat(Boolean etat) {
        this.etat = etat;
    }

    @Override
    public String toString() {
        return "Article{" +
                "nom='" + nom + '\'' +
                ", prix=" + prix +
                ", description='" + description + '\'' +
                ", note='" + note + '\'' +
                ", url='" + url + '\'' +
                ", etat=" + etat +
                '}';
    }

}
