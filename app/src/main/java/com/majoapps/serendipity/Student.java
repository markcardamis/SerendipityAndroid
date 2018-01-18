package com.majoapps.serendipity;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.LinkedHashMap;
import java.util.List;

public class Student implements Parcelable {

    //String msParent;
    //List<String> msChild;
    LinkedHashMap msLinkedHashMap;

    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * Storing the Student data to Parcel object
     **/
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        //dest.writeString(msParent);
        //dest.writeList(msChild);
        dest.writeMap(msLinkedHashMap);
    }

    /**
     * A constructor that initializes the Student object
     **/
    public Student(LinkedHashMap<String, List<String>> slinkedHashMap ){
        this.msLinkedHashMap = slinkedHashMap;
        //this.msParent = sParent;
        //this.msChild = sChild;
    }

    private Student(Parcel in){
        this.msLinkedHashMap = new LinkedHashMap<>();
        in.readMap(this.msLinkedHashMap, LinkedHashMap.class.getClassLoader());
        //this.msLinkHashMap = in.readMap(Student.class.getClassLoader());
        //this.msParent = in.readString();
        //msChild = new ArrayList<String>();
        //in.readList(msChild, Student.class.getClassLoader());

    }

    public static final Parcelable.Creator<Student> CREATOR = new Parcelable.Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel source) {
            return new Student(source);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };
}