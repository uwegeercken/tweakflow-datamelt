# tweakflow-datamelt

Functions for the Tweakstreet ETL tool: https://tweakstreet.io/

module coords, library Coordinates:
- convert UTM coordinates to latitude and longitude
- convert latitude and longitude values to UTM coordinates
- convert degrees in floating point format to degrees, minutes and seconds

module strings, library Soundex:
- get the soundex code representation of a string
- compare two strings if they have the same soundex code (if they sound eimilar)

Run "mvn clean install" to create the jar file. Place the file in the Tweakstreet bin/lib folder and restart the Tweakstreet application.

To use the Coordinates library use an import statement like this: "import coordinates from 'datamelt/coords.tf';".

To use the Soundex library use an import statement like this: "import coordinates from 'datamelt/strings.tf';".

Then call: coordinates.utm2ll() or coordinates.ll2utm() or coordinates.decimal2degrees()
or call: soundex.code() or soundex.equals()

last update: uwe geercken - 2021-03-13

