package Racer.road;

public class Car extends RoadObject{
    public Car(RoadObjectType type, int x, int y) {
        super(type, x, y);
        speed=1;
    }
}