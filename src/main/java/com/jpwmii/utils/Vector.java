package com.jpwmii.utils;

public class Vector {

    public double x;
    public double y;

    public Vector() {
        this.set(0, 0);
    }

    public Vector(double x, double y) {
        this.set(x, y);
    }

    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void add(double dx, double dy) {
        this.x += dx;
        this.y += dy;
    }

    public void multiply(double m) {
        this.x *= m;
        this.y *= m;
    }

    public double getLength() {
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }

    public void setLength(double l) {
        double currentLength = this.getLength();
        if(currentLength == 0) {
            this.set(l, 0);
        } else {
            this.multiply(1 / currentLength);
            this.multiply(l);
        }
    }

    public double getAngle() {
        return Math.toDegrees(Math.atan2(this.y, this.x));
    }

    public void setAngle(double angleDegrees) {
        double l = this.getLength();
        double angleRadians = Math.toRadians(angleDegrees);
        this.x = l * Math.cos(angleRadians);
        this.y = l * Math.sin(angleRadians);
    }

}
