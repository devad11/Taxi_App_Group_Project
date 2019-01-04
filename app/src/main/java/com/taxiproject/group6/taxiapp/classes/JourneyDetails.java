package com.taxiproject.group6.taxiapp.classes;

public class JourneyDetails {

    private PlaceInfo start;
    private PlaceInfo end;
    private double cost;
    private double distanceKm;
    private int duration;

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

    @Override
    public String toString() {
        return "JourneyDetails{" +
                "start=" + start +
                ", end=" + end +
                ", cost=" + cost +
                '}';
    }

}
