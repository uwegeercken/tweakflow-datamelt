# tweakflow-datamelt

Functions for the Tweakstreet ETL tool: https://tweakstreet.io/

module std, library coordinates:
- convert UTM coordinates to latitude and longitude
- convert latitude and longitude values to UTM coordinates
- convert degrees in floating point format to degrees, minutes and seconds

module std, library soundex:
- get the soundex code representation of a string
- compare two strings if they have the same soundex code (if they sound eimilar)

module std, library stringer:
- get the reverse of a string

Run "mvn clean install" to create the jar file. Place the file in the Tweakstreet bin/lib folder or better in the $TWEAKSTREET_HOME/lib folder and restart the Tweakstreet application.

To use the libraries use an import statement like this: "import * as extras from 'datamelt/std';" (you can use a different expression than "extras").
Alternatively you can use the import e.g. like this: "import soundex from 'datamelt/std';", if you only need the soundex functionality.
You can now use the functions: coordinates.utm2ll(), stringer.reverse(), soundex.code(), etc.


last update: uwe geercken - 2022-02-24

