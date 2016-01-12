package com.diorfano.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by keito on 1/11/2016.
 */

public class Cat implements Parcelable {

    private String breed;
    private String image;
    private String colour;
    private String size;
    private int legs;
    private int whiskers;
    private Food prefered_food;


    public Cat(String breed, int legs, String image, String colour, String size, int whiskers) {
        this.setBreed(breed);
        this.setLegs(legs);
        this.setImage(image);
        this.setColour(colour);
        this.setSize(size);
        this.setWhiskers(whiskers);
//        this.setPrefered_food(prefered_food);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(breed);
        dest.writeString(image);
        dest.writeString(colour);
        dest.writeString(size);
        dest.writeInt(legs);
        dest.writeInt(whiskers);
        dest.writeParcelable(prefered_food, flags);
    }

    public static final Parcelable.Creator<Cat> CREATOR = new Parcelable.Creator<Cat>() {
        public Cat createFromParcel(Parcel in) {
            return new Cat(in);
        }

        public Cat[] newArray(int size) {
            return new Cat[size];
        }
    };

    private Cat(Parcel in) {
        breed = in.readString();
        image = in.readString();
        colour = in.readString();
        size = in.readString();
        legs = in.readInt();
        whiskers = in.readInt();
        prefered_food = (Food) in.readParcelable(Food.class.getClassLoader());
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setLegs(int legs) {
        this.legs = legs;
    }

    public void setPrefered_food(Food prefered_food) {
        this.prefered_food = prefered_food;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setWhiskers(int whiskers) {
        this.whiskers = whiskers;
    }

    public String getBreed() {
        return breed;
    }

    public String getColour() {
        return colour;
    }

    public String getImage() {
        return image;
    }

    public int getLegs() {
        return legs;
    }

    public Food getPrefered_food() {
        return prefered_food;
    }

    public String getSize() {
        return size;
    }

    public int getWhiskers() {
        return whiskers;
    }

    @Override
    public String toString() {
        return "Cat{" +
                "breed='" + breed + '\'' +
                ", image='" + image + '\'' +
                ", colour='" + colour + '\'' +
                ", size='" + size + '\'' +
                ", legs=" + legs +
                ", whiskers=" + whiskers +
                ", prefered_food=" + prefered_food +
                ", CREATOR=" + CREATOR +
                '}';
    }

    public static class Food implements Parcelable {
        private String name;
        private String package1;

        public Food(String name, String package1) {
            this.setName(name);
            this.setPackage1(package1);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(name);
            dest.writeString(package1);
        }

        public static final Parcelable.Creator<Food> CREATOR = new Parcelable.Creator<Food>() {
            public Food createFromParcel(Parcel in) {
                return new Food(in);
            }

            public Food[] newArray(int size) {
                return new Food[size];
            }
        };

        private Food(Parcel in) {
            name = in.readString();
            package1 = in.readString();
        }

        @Override
        public String toString() {
            return "Food{" +
                    "name='" + name + '\'' +
                    ", package1='" + package1 + '\'' +
                    '}';
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPackage1() {
            return package1;
        }

        public void setPackage1(String package1) {
            this.package1 = package1;
        }
    }
}
