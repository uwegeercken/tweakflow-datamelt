package com.datamelt.tweakflow.coords;

import com.twineworks.tweakflow.lang.values.*;

import java.util.HashMap;
import java.util.Map;

public class Coordinates
{
    // following variables are valid for ellipsoid = "WGS84" only
    private static final double a = 6378137.0;
    private static final double f = 1 / 298.257223563;
    private static final double e2 = 1 - (1 - f) * (1 - f);

    public static final class decimal2degrees implements UserFunction, Arity2UserFunction
    {
        @Override
        public Value call(UserCallContext context, Value lat_decimal, Value long_decimal)
        {
            if(lat_decimal.isNil() || (!lat_decimal.isLongNum() && !lat_decimal.isDoubleNum()) || long_decimal.isNil() || (!long_decimal.isLongNum() && !long_decimal.isDoubleNum()))
            {
                return Values.NIL;
            }

            double latitude_decimal = 0;
            double longitude_decimal = 0;

            if(lat_decimal.isLongNum())
            {
                latitude_decimal = 1.0d * lat_decimal.longNum();
            }
            if(long_decimal.isLongNum() )
            {
                longitude_decimal = 1.0d * long_decimal.longNum();
            }

            if(lat_decimal.isDoubleNum())
            {
                latitude_decimal = lat_decimal.doubleNum();
            }
            if(long_decimal.isDoubleNum() )
            {
                longitude_decimal = long_decimal.doubleNum();
            }

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

    public static final class ll2utm implements UserFunction, Arity2UserFunction {

        private int ll2zone(double latitude, double longitude)
        {
            if(56 <= latitude && latitude < 64 && 3 <= longitude && longitude < 12)
            {
                return 32;
            }
            if(72 <= latitude && latitude <= 84 && longitude >= 0) {
                if (longitude < 9) {
                    return 31;
                } else if (longitude < 21) {
                    return 33;
                } else if (longitude < 33) {
                    return 35;
                } else if (longitude < 42) {
                    return 37;
                }
            }
            return (int)((longitude + 180) / 6) + 1;
        }

        @Override
        public Value call(UserCallContext context, Value latitude, Value longitude) {

            if(latitude.isNil() || (!latitude.isLongNum() && !latitude.isDoubleNum()) || longitude.isNil() || (!longitude.isLongNum() && !longitude.isDoubleNum()))
            {
                return Values.NIL;
            }

            double lat = 0;
            double lon = 0;

            if(latitude.isLongNum())
            {
                lat = 1.0d * latitude.longNum();
            }
            if(longitude.isLongNum() )
            {
                lon = 1.0d * longitude.longNum();
            }

            if(latitude.isDoubleNum())
            {
                lat = latitude.doubleNum();
            }
            if(longitude.isDoubleNum() )
            {
                lon = longitude.doubleNum();
            }

            double K0=0.9996;
            double E2 = e2 * e2;
            double E3 = E2 * e2;

            double E_P2 = e2 / (1.0 - e2);

            double M1 = (1 - e2 / 4 - 3 * E2 / 64 - 5 * E3 / 256);
            double M2 = (3 * e2 / 8 + 3 * E2 / 32 + 45 * E3 / 1024);
            double M3 = (15 * E2 / 256 + 45 * E3 / 1024);
            double M4 = (35 * E3 / 3072);

            double lat_rad = Math.toRadians(lat);
            double lat_sin = Math.sin(lat_rad);
            double lat_cos = Math.cos(lat_rad);

            double lat_tan = lat_sin / lat_cos;
            double lat_tan2 = lat_tan * lat_tan;
            double lat_tan4 = lat_tan2 * lat_tan2;

            int zone = ll2zone(lat, lon);

            double lon_rad = Math.toRadians(lon);
            long central_lon = (zone - 1) * 6 - 180 + 3;
            double central_lon_rad = Math.toRadians(central_lon);

            double n = Coordinates.a / Math.sqrt(1 - (e2 * lat_sin * lat_sin));
            double c = E_P2 * (lat_cos * lat_cos);

            double a = lat_cos * (lon_rad - central_lon_rad);
            double a2 = a * a;
            double a3 = a2 * a;
            double a4 = a3 * a;
            double a5 = a4 * a;
            double a6 = a5 * a;

            double m = Coordinates.a * (M1 * lat_rad - M2 * Math.sin(2 * lat_rad) + M3 * Math.sin(4 * lat_rad) - M4 * Math.sin(6 * lat_rad));

            double easting = K0 * n * (a + a3 / 6 * (1 - lat_tan2 + c) + a5 / 120 * (5 - 18 * lat_tan2 + lat_tan4 + 72 * c - 58 * E_P2)) + 500000;
            double northing = K0 * (m + n * lat_tan * (a2 / 2 + a4 / 24 * (5 - lat_tan2 + 9 * c + (4 * c*c)) + a6 / 720 * (61 - 58 * lat_tan2 + lat_tan4 + 600 * c - 330 * E_P2)));

            String hemis="N";
            if(lat < 0) {
                northing += 10000000;
                hemis = "S";
            }

            Map<String, Value> coordinates = new HashMap<String, Value>();
            coordinates.put("easting",Values.make(easting));
            coordinates.put("northing",Values.make(northing));
            coordinates.put("zone",Values.make(zone));
            coordinates.put("hemisphere",Values.make(hemis));

            final Value value = Values.makeDict(coordinates);
            return value;

        }
    }

    public static final class utm2ll implements UserFunction, Arity4UserFunction
    {
        @Override
        public Value call(UserCallContext context, Value easting, Value northing, Value zone, Value hemis)
        {
            if(easting.isNil() || northing.isNil() || zone.isNil() || hemis.isNil())
            {
                return Values.NIL;
            }

            if(!easting.isLongNum() || !northing.isLongNum() || !zone.isLongNum())
            {
                return Values.NIL;
            }

            if(!hemis.isString())
            {
                return Values.NIL;
            }

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
            coordinates.put("latitude",Values.make(Math.toDegrees(lat)));
            coordinates.put("longitude",Values.make(Math.toDegrees(lon) + cm));

            final Value value = Values.makeDict(coordinates);
            return value;
        }
    }

    public static void main(String[] args)
    {
        utm2ll u = new Coordinates.utm2ll();
        decimal2degrees dd = new Coordinates.decimal2degrees();

        Value val = u.call(null,Values.make(572497),Values.make(6013607),Values.make(32) ,Values.make("U"));

        Value val2 = dd.call(null,val.dict().get("latitude"),val.dict().get("longitude"));

        System.out.println("Lat/long dec: " + val.toString());

        System.out.println("Decimals to degrees: " + val2.toString());

        ll2utm lu = new Coordinates.ll2utm();

        Value val3 = lu.call(null, val.dict().get("latitude"),val.dict().get("longitude"));

        System.out.println("Lat/long to UTM: " + val3.toString());




    }
}
