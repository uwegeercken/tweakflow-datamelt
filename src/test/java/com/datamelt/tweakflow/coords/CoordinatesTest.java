package com.datamelt.tweakflow.coords;

import com.twineworks.tweakflow.lang.values.UserCallContext;
import com.twineworks.tweakflow.lang.values.Value;
import com.twineworks.tweakflow.lang.values.Values;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoordinatesTest {

    private final UserCallContext context = null;

    @Test
    void testSampleValueLatitude() {

        Coordinates.utm2ll u = new Coordinates.utm2ll();

        Value value = u.call(context, Values.make(572497), Values.make(6013607), Values.make(32), Values.make("N"));
        double latitude = value.dict().get("latitude").doubleNum();

        assertEquals(54.2652606393547, latitude);
    }

    @Test
    void testSampleValueLongitude() {

        Coordinates.utm2ll u = new Coordinates.utm2ll();

        Value value = u.call(context, Values.make(572497), Values.make(6013607), Values.make(32), Values.make("N"));
        double longitude = value.dict().get("longitude").doubleNum();

        assertEquals(10.113098409646113, longitude);
    }

    @Test
    void testEastingNull() {

        Coordinates.utm2ll u = new Coordinates.utm2ll();

        Value value = u.call(context, Values.NIL, Values.make(6013607), Values.make(32), Values.make("N"));

        assertTrue(value.isNil());
    }
    @Test
    void testNorthingNull() {

        Coordinates.utm2ll u = new Coordinates.utm2ll();

        Value value = u.call(context, Values.make(572497), Values.NIL, Values.make(32), Values.make("N"));

        assertTrue(value.isNil());
    }

    @Test
    void testZoneNull() {

        Coordinates.utm2ll u = new Coordinates.utm2ll();

        Value value = u.call(context, Values.make(572497), Values.make(6013607), Values.NIL, Values.make("N"));

        assertTrue(value.isNil());
    }

    @Test
    void testHemisNull() {

        Coordinates.utm2ll u = new Coordinates.utm2ll();

        Value value = u.call(context, Values.make(572497), Values.make(6013607), Values.make(32), Values.NIL);

        assertTrue(value.isNil());
    }

}