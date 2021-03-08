# tweakflow-datamelt

Functions for the Tweakstreet ETL tool: https://tweakstreet.io/

Library Coordinates:
- convert UTM coordinates to latitude and longitude
- convert latitude and longitude values to UTM coordinates
- convert degrees in floating point format to degrees, minutes and seconds

Run "mvn clean install" to create the jar file. Place the file in the Tweakstreet bin/lib folder and restart the Tweakstreet application.

To use the libtary use an import statement like this: "import coordinates from 'datamelt/coords.tf';".

Then call: coordinates.utm2ll() or coordinates.ll2utm() or coordinates.decimal2degrees()

last update: uwe geercken - 2021-03-08

