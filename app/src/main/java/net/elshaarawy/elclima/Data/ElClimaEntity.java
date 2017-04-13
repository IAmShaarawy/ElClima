package net.elshaarawy.elclima.Data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by elshaarawy on 13-Apr-17.
 */

public class ElClimaEntity implements Parcelable {
    private long date;
    private float tDay, tMin, tMax, tNight, tEvening, tMorning, pressure, speed;
    private byte humidity;
    private int wID, deg, clouds;
    private String wMain, wDescription, wIcon;

    public ElClimaEntity(long date, float tDay, float tMin, float tMax, float tNight, float tEvening, float tMorning, float pressure, float speed, byte humidity, int wID, int deg, int clouds, String wMain, String wDescription, String wIcon) {
        this.date = date;
        this.tDay = tDay;
        this.tMin = tMin;
        this.tMax = tMax;
        this.tNight = tNight;
        this.tEvening = tEvening;
        this.tMorning = tMorning;
        this.pressure = pressure;
        this.speed = speed;
        this.humidity = humidity;
        this.wID = wID;
        this.deg = deg;
        this.clouds = clouds;
        this.wMain = wMain;
        this.wDescription = wDescription;
        this.wIcon = wIcon;
    }

    protected ElClimaEntity(Parcel in) {
        date = in.readLong();
        tDay = in.readFloat();
        tMin = in.readFloat();
        tMax = in.readFloat();
        tNight = in.readFloat();
        tEvening = in.readFloat();
        tMorning = in.readFloat();
        pressure = in.readFloat();
        speed = in.readFloat();
        humidity = in.readByte();
        wID = in.readInt();
        deg = in.readInt();
        clouds = in.readInt();
        wMain = in.readString();
        wDescription = in.readString();
        wIcon = in.readString();
    }

    public static final Creator<ElClimaEntity> CREATOR = new Creator<ElClimaEntity>() {
        @Override
        public ElClimaEntity createFromParcel(Parcel in) {
            return new ElClimaEntity(in);
        }

        @Override
        public ElClimaEntity[] newArray(int size) {
            return new ElClimaEntity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(date);
        dest.writeFloat(tDay);
        dest.writeFloat(tMin);
        dest.writeFloat(tMax);
        dest.writeFloat(tNight);
        dest.writeFloat(tEvening);
        dest.writeFloat(tMorning);
        dest.writeFloat(pressure);
        dest.writeFloat(speed);
        dest.writeByte(humidity);
        dest.writeInt(wID);
        dest.writeInt(deg);
        dest.writeInt(clouds);
        dest.writeString(wMain);
        dest.writeString(wDescription);
        dest.writeString(wIcon);
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public float gettDay() {
        return tDay;
    }

    public void settDay(float tDay) {
        this.tDay = tDay;
    }

    public float gettMin() {
        return tMin;
    }

    public void settMin(float tMin) {
        this.tMin = tMin;
    }

    public float gettMax() {
        return tMax;
    }

    public void settMax(float tMax) {
        this.tMax = tMax;
    }

    public float gettNight() {
        return tNight;
    }

    public void settNight(float tNight) {
        this.tNight = tNight;
    }

    public float gettEvening() {
        return tEvening;
    }

    public void settEvening(float tEvening) {
        this.tEvening = tEvening;
    }

    public float gettMorning() {
        return tMorning;
    }

    public void settMorning(float tMorning) {
        this.tMorning = tMorning;
    }

    public float getPressure() {
        return pressure;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public byte getHumidity() {
        return humidity;
    }

    public void setHumidity(byte humidity) {
        this.humidity = humidity;
    }

    public int getwID() {
        return wID;
    }

    public void setwID(int wID) {
        this.wID = wID;
    }

    public int getDeg() {
        return deg;
    }

    public void setDeg(int deg) {
        this.deg = deg;
    }

    public int getClouds() {
        return clouds;
    }

    public void setClouds(int clouds) {
        this.clouds = clouds;
    }

    public String getwMain() {
        return wMain;
    }

    public void setwMain(String wMain) {
        this.wMain = wMain;
    }

    public String getwDescription() {
        return wDescription;
    }

    public void setwDescription(String wDescription) {
        this.wDescription = wDescription;
    }

    public String getwIcon() {
        return wIcon;
    }

    public void setwIcon(String wIcon) {
        this.wIcon = wIcon;
    }
}
