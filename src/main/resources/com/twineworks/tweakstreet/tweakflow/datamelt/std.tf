/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for theyccyxcc
 * specific language governing permissions and limitations
 * under the License.
 */

doc
~~~
The `std` module contains libraries for various functions.
~~~

module;

doc
~~~
The soundex library contains functions using the soundex algorithm.
~~~

export library soundex {

doc
~~~
`(string value) -> string`

Convert string value to its soundex code representation.

Returns nil if expected values is nil.

```tweakflow
> soundex.code("Hamburg")
H516

```
~~~
  function code: (string value)  -> via {:class "com.datamelt.tweakflow.std.Soundex$code"};

doc
~~~
`(string value, string comparevalue) -> boolean`

Compare two string values using their soundex code representation.

Returns true if the soundex code of the two values is the same otherwise returns false.

Returns nil if any of the expected values is nil.

```tweakflow
> soundex.equals("Hamburg", "Himmburgg")
true

```
~~~
  function equals: (string value, string comparevalue)  -> via {:class "com.datamelt.tweakflow.std.Soundex$equals"};

}

doc
~~~
The coordinates library contains functions for converting coordinates.
~~~

export library coordinates {

doc
~~~
`(long easting, long northing, long zone, string hemis) -> dict`

Easting and northing are UTM coordinates, Zone is the numeric value of the UTM zone and hemis defines the hemisphere of the zone (N or S).
The ellipsoid the coordinates are based on must be WGS84.

Returns a dict consisting of a latitude and longitude value - converted from UTM.

Returns nil if any of the expected values is nil.

```tweakflow
> coordinates.utm2ll(572497, 6013607, 32, "N")
{
  :latitude 54.2652606393547,
  :longitude 10.113098409646113
}
```
~~~
  function utm2ll: (long easting, long northing, long zone, string hemis)  -> via {:class "com.datamelt.tweakflow.std.Coordinates$utm2ll"};

doc
~~~
`(double latitude, double longitude) -> dict`

Returns a dict consisting of values for easting, northing, zone and hemisphere - converted from the latitude and longitude values.

Returns nil if any of the expected values is nil or not of type double or long.

```tweakflow
> coordinates.ll2utm(54.2652606393547, 10.113098409646113)
{
  :easting 572497.0026859988,
  :zone 32,
  :hemis "N",
  :northing 6013607.000382532
}
```
~~~
  function ll2utm: (double latitude, double longitude)  -> via {:class "com.datamelt.tweakflow.std.Coordinates$ll2utm"};

doc
~~~
`(double latitude, double longitude) -> dict`

Latitude and longitude values are converted to degrees, minutes and seconds.

Returns nil if any of the expected values is nil or not of type double or long.

```tweakflow
> coordinates.decimal2degrees(54.2652606393547, 10.113098409646113)
{
  :latitude {
    :degrees 54,
    :minutes 15,
    :seconds 54.93830167692124
  },
  :longitude {
    :degrees 10,
    :minutes 6,
    :seconds 47.154274726008225
  }
}
```
~~~
  function decimal2degrees: (double latitude, double longitude)  -> via {:class "com.datamelt.tweakflow.std.Coordinates$decimal2degrees"};

}

