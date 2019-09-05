package apps.searchme.sm;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

@SuppressLint("ParcelCreator")
public class profile_mode implements Parcelable {
    String name;
    String profession;
    String country_name;
    String mobile;
    String img_url;
    String 	current_latitude;
    String current_longitude;
    String mac_address;
    String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMac_address() {
        return mac_address;
    }

    public void setMac_address(String mac_address) {
        this.mac_address = mac_address;
    }

    public String getCurrent_latitude() {
        return current_latitude;
    }

    public static final Parcelable.Creator<profile_mode> CREATOR = new Parcelable.Creator<profile_mode>() {
        public profile_mode createFromParcel(Parcel in) {
            return new profile_mode(in);
        }

        public profile_mode[] newArray(int size) {
            return new profile_mode[size];

        }
    };

    public void setCurrent_latitude(String current_latitude) {
        this.current_latitude = current_latitude;
    }

    public String getCurrent_longitude() {
        return current_longitude;
    }

    public void setCurrent_longitude(String current_longitude) {
        this.current_longitude = current_longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public profile_mode(Parcel arrayListClass) {
    }

    public profile_mode(String name, String profession, String country_name, String mobile, String img_url,String current_latitude,String current_longitude,String mac_address,String status) {
        this.name = name;
        this.profession = profession;
        this.country_name = country_name;
        this.mobile = mobile;
        this.img_url = img_url;
        this.current_latitude = current_latitude;
        this.current_longitude = current_longitude;
        this.mac_address = mac_address;
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(profession);
        parcel.writeString(country_name);
        parcel.writeString(mobile);
        parcel.writeString(img_url);
        parcel.writeString(current_latitude);
        parcel.writeString(current_longitude);
    }


}
