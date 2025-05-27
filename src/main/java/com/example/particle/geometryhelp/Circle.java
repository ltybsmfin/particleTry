package com.example.particle.geometryhelp;

import org.joml.Math;
import org.joml.Vector3f;


public class Circle
{
    Vector3f WorldPosition = new Vector3f(0, 0, 0);
    Vector3f normal = new Vector3f(0, 1, 0);
    Vector3f[] WorldAxis =
            {
            new Vector3f(1, 0, 0),
            new Vector3f(0, 1, 0),
            new Vector3f(0, 0, 1)
            };


    float PI = 3.1415f;
    float radius;
    int resolution;
    //Vector3f normal = new Vector3f(0f, 1f, 0f);

    public Circle(int resolution, float radius)
    {
        this.radius = radius;
        this.resolution = resolution;
    }

    public Vector3f GetObjectPoint(int index)
    {
        float angle =  (float)index / resolution * PI * 2;
        float x = radius * Math.sin(angle);
        float y = 0f;
        float z = radius * Math.cos(angle);

        return new Vector3f(x, y, z);
    }

//    public Vector3f GetWorldPoint(int index)
//    {
//
//    }
}