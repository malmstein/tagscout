# TagScout

### Summary

TagScout can be run using two different flavours. The reason for that is to create an environment where the unit, integration and, most importantly, instrumentation tests, can be run much faster.  Also, it makes the application usable should the remote API service be down.

Once the repository is cloned, these are the CLI needed in order to build and run both flavours:

`$./gradleW assembleMock` - Generates the APK for the mock flavour
`$./gradleW assembleProd` - Generates the APK for the production flavour

Run the tests

`$./gradleW clean build` - Builds all the flavours and runs the unit tests
`$./gradleW connectedMockDebugAndroidTest` - Runs the instrumentation tests (using Espresso)
