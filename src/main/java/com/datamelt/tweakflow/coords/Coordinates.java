package com.datamelt.tweakflow.coords;

import com.twineworks.tweakflow.lang.values.*;

import java.util.HashMap;
import java.util.Map;

public class Coordinates
{
    public static final class decimal2degrees implements UserFunction, Arity2UserFunction
    {
        @Override
        public Value call(UserCallContext context, Value lat_decimal, Value long_decimal)
        {
            double latitude_decimal = lat_decimal.doubleNum();
            double longitude_decimal = long_decimal.doubleNum();

            double latitude_minutes = (latitude_decimal - (long)latitude_decimal) * 60;
            double latitude_seconds = (latitude_minutes - (long)latitude_minutes) * 60;
            double longitude_minutes = (longitude_decimal - (long)longitude_decimal) * 60;
            double longitude_seconds = (longitude_minutes - (long)longitude_minutes) * 60;

            Map<String, Value> latitude = new HashMap<>();
            latitude.put("degrees",Values.make((long)latitude_decimal));
            latitude.put("minutes",Values.make((long)latitude_minutes));
            latitude.put("seconds",Values.make(latitude_seconds));

            Map<String, Value> longitude = new HashMap<>();
            longitude.put("degrees",Values.make((long)longitude_decimal));
            longitude.put("minutes",Values.make((long)longitude_minutes));
            longitude.put("seconds",Values.make(longitude_seconds));

            Map<String, Value> coordinates = new HashMap<>();
            coordinates.put("latitude", Values.make(latitude));
            coordinates.put("longitude", Values.make(longitude));

            return Values.makeDict(coordinates);
        }
    }
    public static final class utm2ll implements UserFunction, Arity4UserFunction
    {
        private static final double pi = java.lang.Math.PI;
        private static final double oneRadian = 180 / pi;
        private static final String ellipsoid = "WGS84";
        private static final double a = 6378137.0;
        private static final double f = 1 / 298.257223563;
        private static final double e2 = 1 - (1 - f) * (1 - f);

        @Override
        public Value call(UserCallContext context, Value easting, Value northing, Value zone, Value hemis)
        {

            long x = easting.longNum() - 500000;
            long y = northing.longNum();

            if (hemis.string().toUpperCase().equals('S'))
            {
                y -= 10000000;
            }
            long cm =  (zone.longNum() - 1) * 6 - 180 + 3;

            double K0 = 0.9996;
            double E2 = e2 * e2;
            double E3 = E2 * e2;
            double E_P2 = e2 / (1.0 - e2);
            double _E = (1 - Math.sqrt(1 - e2)) / (1 + Math.sqrt(1 - e2));
            double _E2 = _E * _E;
            double _E3 = _E2 * _E;
            double _E4 = _E3 * _E;
            double _E5 = _E4 * _E;

            double M1 = (1 - e2 / 4 - 3 * E2 / 64 - 5 * E3 / 256);
            double P2 = (3. / 2 * _E - 27. / 32 * _E3 + 269. / 512 * _E5);
            double P3 = (21. / 16 * _E2 - 55. / 32 * _E4);
            double P4 = (151. / 96 * _E3 - 417. / 128 * _E5);
            double P5 = (1097. / 512 * _E4);

            double m = y / K0;
            double mu = m / (a * M1);

            double p_rad = (mu + P2 * Math.sin(2 * mu) + P3 * Math.sin(4 * mu) + P4 * Math.sin(6 * mu) + P5 * Math.sin(8 * mu));

            double p_sin = Math.sin(p_rad);
            double p_sin2 = p_sin * p_sin;
            double p_cos = Math.cos(p_rad);
            double p_tan = p_sin / p_cos;
            double p_tan2 = p_tan * p_tan;
            double p_tan4 = p_tan2 * p_tan2;

            double ep_sin = 1 - e2 * p_sin2;
            double ep_sin_sqrt = Math.sqrt(1 - e2 * p_sin2);

            double n = a / ep_sin_sqrt;
            double r = (1 - e2) / ep_sin;

            double c = _E * (p_cos * p_cos); //
            double c2 = c * c;

            double d = x / (n * K0);
            double d2 = d * d;
            double d3 = d2 * d;
            double d4 = d3 * d;
            double d5 = d4 * d;
            double d6 = d5 * d;

            double lat = (p_rad - (p_tan / r) * (d2 / 2 - d4 / 24 * (5 + 3 * p_tan2 + 10 * c - 4 * c2 - 9 * E_P2)) + d6 / 720 * (61 + 90 * p_tan2 + 298 * c + 45 * p_tan4 - 252 * E_P2 - 3 * c2));

            double lon = (d - d3 / 6 * (1 + 2 * p_tan2 + c) + d5 / 120 * (5 - 2 * c + 28 * p_tan2 - 3 * c2 + 8 * E_P2 + 24 * p_tan4)) / p_cos;

            Map<String, Value> coordinates = new HashMap<String, Value>();
            coordinates.put("latitude",Values.make(lat * oneRadian ));
            coordinates.put("longitude",Values.make(lon * oneRadian + cm));

            final Value value = Values.makeDict(coordinates);
            return value;
        }
    }

    public static void main(String[] args)
    {
        utm2ll u = new Coordinates.utm2ll();

        Value val = u.call(null,Values.make(572497),Values.make(6013607),Values.make(32) ,Values.make("U"));

        System.out.println("Latitude: " + val.dict().get("latitude"));
        System.out.println("Longitude: " + val.dict().get("longitude"));

        Value lat = val.dict().get("latitude");
        Value lon = val.dict().get("longitude");

        decimal2degrees d = new Coordinates.decimal2degrees();

        Value val2 = d.call(null, lat, lon);

        System.out.println("Latitude Degrees: " + val2.dict().get("latitude"));
        System.out.println("Longitude Degrees: " + val2.dict().get("longitude"));
    }
}
