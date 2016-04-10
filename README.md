# TagScout

Android sample that display a list of tags and allows the user to select them. One can toggle the selection state of the tag by clicking on the list item or on the tag itself.

| Filter | Tags |
| ------ | ----- |
| ![filter](https://raw.githubusercontent.com/malmstein/tagscout/master/art/filter.png) | ![tags](https://raw.githubusercontent.com/malmstein/tagscout/master/art/tags.png) |


## Build it

TagScout can be run using two different flavours. The reason for that is to create an environment where the unit, integration and, most importantly, instrumentation tests, can be run much faster.  Also, it makes the application usable should the remote API service be down.

Once the repository is cloned, these are the CLI needed in order to build and run both flavours:

* `$./gradleW assembleMock` - Generates the APK for the mock flavour
* `$./gradleW assembleProd` - Generates the APK for the production flavour

## Tests

Run the tests, you'll need to connect an Android device or emulator to run the UI tests

* `$./gradleW clean build` - Builds all the flavours and runs the unit tests
* `$./gradleW connectedMockDebugAndroidTest` - Runs the instrumentation tests (using Espresso)
