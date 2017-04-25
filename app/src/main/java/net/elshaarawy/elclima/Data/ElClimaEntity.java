package net.elshaarawy.elclima.Data;

import android.os.DeadObjectException;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by elshaarawy on 13-Apr-17.
 */

public class ElClimaEntity implements Parcelable {
    private long date;
    private double tDay, tMin, tMax, tNight, tEvening, tMorning, pressure, speed;
    private int humidity, wID, deg, clouds;
    private String wMain, wDescription, wIcon;

    public ElClimaEntity(long date, double tDay, double tMin, double tMax, double tNight, double tEvening, double tMorning, double pressure, double speed, int humidity, int wID, int deg, int clouds, String wMain, String wDescription, String wIcon) {
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
        dest.writeDouble(tDay);
        dest.writeDouble(tMin);
        dest.writeDouble(tMax);
        dest.writeDouble(tNight);
        dest.writeDouble(tEvening);
        dest.writeDouble(tMorning);
        dest.writeDouble(pressure);
        dest.writeDouble(speed);
        dest.writeInt(humidity);
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

    public double gettDay() {
        return tDay;
    }

    public void settDay(double tDay) {
        this.tDay = tDay;
    }

    public double gettMin() {
        return tMin;
    }

    public void settMin(double tMin) {
        this.tMin = tMin;
    }

    public double gettMax() {
        return tMax;
    }

    public void settMax(double tMax) {
        this.tMax = tMax;
    }

    public double gettNight() {
        return tNight;
    }

    public void settNight(double tNight) {
        this.tNight = tNight;
    }

    public double gettEvening() {
        return tEvening;
    }

    public void settEvening(double tEvening) {
        this.tEvening = tEvening;
    }

    public double gettMorning() {
        return tMorning;
    }

    public void settMorning(double tMorning) {
        this.tMorning = tMorning;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getHumidity() {
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

    public static Creator<ElClimaEntity> getCREATOR() {
        return CREATOR;
    }
}
