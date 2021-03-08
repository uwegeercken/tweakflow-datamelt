package com.datamelt.tweakflow.coords;

import com.twineworks.tweakflow.lang.values.UserCallContext;
import com.twineworks.tweakflow.lang.values.Value;
import com.twineworks.tweakflow.lang.values.Values;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoordinatesTest {

    private final UserCallContext context = null;

    @Test
    void testUtm2llSampleValueLatitude() {
        Coordinates.utm2ll u = new Coordinates.utm2ll();
        Value value = u.call(context, Values.make(572497), Values.make(6013607), Values.make(32), Values.make("N"));
        double latitude = value.dict().get("latitude").doubleNum();

        assertEquals(54.2652606393547, latitude);
    }

    @Test
    void testUtm2llSampleValueLongitude() {
        Coordinates.utm2ll u = new Coordinates.utm2ll();
        Value value = u.call(context, Values.make(572497), Values.make(6013607), Values.make(32), Values.make("N"));
        double longitude = value.dict().get("longitude").doubleNum();

        assertEquals(10.113098409646113, longitude);
    }

    @Test
    void testUtm2llEastingNull() {
        Coordinates.utm2ll u = new Coordinates.utm2ll();
        Value value = u.call(context, Values.NIL, Values.make(6013607), Values.make(32), Values.make("N"));

        assertTrue(value.isNil());
    }
    @Test
    void testUtm2llNorthingNull() {
        Coordinates.utm2ll u = new Coordinates.utm2ll();
        Value value = u.call(context, Values.make(572497), Values.NIL, Values.make(32), Values.make("N"));

        assertTrue(value.isNil());
    }

    @Test
    void testUtm2llZoneNull() {
        Coordinates.utm2ll u = new Coordinates.utm2ll();
        Value value = u.call(context, Values.make(572497), Values.make(6013607), Values.NIL, Values.make("N"));

        assertTrue(value.isNil());
    }

    @Test
    void testUtm2llHemisNull() {
        Coordinates.utm2ll u = new Coordinates.utm2ll();
        Value value = u.call(context, Values.make(572497), Values.make(6013607), Values.make(32), Values.NIL);

        assertTrue(value.isNil());
    }

    @Test
    void testLl2utmLatitudeNUll() {
        Coordinates.ll2utm l = new Coordinates.ll2utm();
        Value value = l.call(context, Values.NIL, Values.make(10.113098409646113));

        assertTrue(value.isNil());
    }

    @Test
    void testLl2utmLongitudeNUll() {
        Coordinates.ll2utm l = new Coordinates.ll2utm();
        Value value = l.call(context, Values.make(54.2652606393547), Values.NIL);

        assertTrue(value.isNil());
    }

    @Test
    void testDecimal2degreesLatitudeNUll() {
        Coordinates.decimal2degrees d = new Coordinates.decimal2degrees();
        Value value = d.call(context, Values.NIL, Values.make(10.113098409646113));

        assertTrue(value.isNil());
    }

    @Test
    void testDecimal2degreesLongitudeNUll() {
        Coordinates.decimal2degrees d = new Coordinates.decimal2degrees();
        Value value = d.call(context, Values.make(54.2652606393547), Values.NIL);

        assertTrue(value.isNil());
    }

    @Test
    void testLl2utmSampleValueLatitude() {
        Coordinates.ll2utm l = new Coordinates.ll2utm();
        Value value = l.call(context, Values.make(54.06982125373037), Values.make(9.241397640265722));
        double easting = value.dict().get("easting").doubleNum();

        assertEquals(515797.0000280403, easting);
    }

    @Test
    void testLl2utmSampleValueLongitude() {
        Coordinates.ll2utm l = new Coordinates.ll2utm();
        Value value = l.call(context, Values.make(54.06982125373037), Values.make(9.241397640265722));
        double northing = value.dict().get("northing").doubleNum();

        assertEquals(5991317.000420985, northing);
    }

    @Test
    void testDecimal2degreesSampleValueLatitudeDegrees() {
        Coordinates.decimal2degrees d = new Coordinates.decimal2degrees();
        Value value = d.call(context, Values.make(54.255505588886884), Values.make(9.010990446997248));
        long degrees = value.dict().get("latitude").dict().get("degrees").longNum();

        assertEquals(54, degrees);
    }

    @Test
    void testDecimal2degreesSampleValueLatitudeMinutes() {
        Coordinates.decimal2degrees d = new Coordinates.decimal2degrees();
        Value value = d.call(context, Values.make(54.255505588886884), Values.make(9.010990446997248));
        long minutes = value.dict().get("latitude").dict().get("minutes").longNum();

        assertEquals(15, minutes);
    }

    @Test
    void testDecimal2degreesSampleValueLatitudeSeconds() {
        Coordinates.decimal2degrees d = new Coordinates.decimal2degrees();
        Value value = d.call(context, Values.make(54.255505588886884), Values.make(9.010990446997248));
        double seconds = value.dict().get("latitude").dict().get("seconds").doubleNum();

        assertEquals(19.82011999278086, seconds);
    }

    //  :longitude {
    //    :degrees 9,
    //    :minutes 0,
    //    :seconds 39.56560919009178

    @Test
    void testDecimal2degreesSampleValueLongitudeDegrees() {
        Coordinates.decimal2degrees d = new Coordinates.decimal2degrees();
        Value value = d.call(context, Values.make(54.255505588886884), Values.make(9.010990446997248));
        long degrees = value.dict().get("longitude").dict().get("degrees").longNum();

        assertEquals(9, degrees);
    }

    @Test
    void testDecimal2degreesSampleValueLongitudeMinutes() {
        Coordinates.decimal2degrees d = new Coordinates.decimal2degrees();
        Value value = d.call(context, Values.make(54.255505588886884), Values.make(9.010990446997248));
        long minutes = value.dict().get("longitude").dict().get("minutes").longNum();

        assertEquals(0, minutes);
    }

    @Test
    void testDecimal2degreesSampleValueLongitudeSeconds() {
        Coordinates.decimal2degrees d = new Coordinates.decimal2degrees();
        Value value = d.call(context, Values.make(54.255505588886884), Values.make(9.010990446997248));
        double seconds = value.dict().get("longitude").dict().get("seconds").doubleNum();

        assertEquals(39.56560919009178, seconds);
    }

}