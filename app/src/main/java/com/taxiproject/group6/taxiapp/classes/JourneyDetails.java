package com.taxiproject.group6.taxiapp.classes;

import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

public class JourneyDetails {

    private PlaceInfo start;
    private PlaceInfo end;
    private double cost;
    private double distanceKm;
    private int duration;
    private String date;
    private String time;

    public JourneyDetails() {
    }

    public JourneyDetails(PlaceInfo start, PlaceInfo end, double cost,
                          double distanceKm, int duration, String date, String time) {
        this.start = start;
        this.end = end;
        this.cost = cost;
        this.distanceKm = distanceKm;
        this.duration = duration;
        this.date = date;
        this.time = time;
    }

    public PlaceInfo getStart() {
        return start;
    }

    public void setStart(PlaceInfo start) {
        this.start = start;
    }

    public PlaceInfo getEnd() {
        return end;
    }

    public void setEnd(PlaceInfo end) {
        this.end = end;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getDistanceKm() {
        return distanceKm;
    }

    public void setDistanceKm(double distanceKm) {
        this.distanceKm = distanceKm;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    //    public String getPlaceFrom() {
//        return placeFrom;
//    }
//
//    public void setPlaceFrom(String placeFrom) {
//        this.placeFrom = placeFrom;
//    }
//
//    public String getPlaceTo() {
//        return placeTo;
//    }
//
//    public void setPlaceTo(String placeTo) {
//        this.placeTo = placeTo;
//    }

    public Map<String, Object> toMap() {
        //    private String placeFrom;
        //    private String placeTo;
        Map<String, Object> result = new HashMap<>();
        result.put("start", this.start);
        result.put("end", this.end);
        result.put("cost", this.cost);
        result.put("distanceKm", this.distanceKm);
        result.put("duration", this.duration);
        result.put("date", this.date);
        result.put("time", this.time);

        return result;
    }

    @NonNull
    @Override
    public String toString() {
        return "JourneyDetails{" +
                "start=" + start +
                ", end=" + end +
                ", cost=" + cost +
                ", distanceKm=" + distanceKm +
                ", duration=" + duration +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
