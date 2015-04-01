This page gives basic information on how to run an Android test project with code coverage evaluated via emma.



# Preconditions #

This howto assumes that:

  * **ant** is installed and its _bin_ subdirectory is in the System Path
  * the **Android SDK r-15** is installed and its _tools_ and _platform-tools_ subdirs are in the System Path
  * the **crawler** source code is ready to compile under the %CRAWLER\_DIR% directory (you might eventually work on a copy of the original dir)
  * the **application under test** is %APP\_NAME%
  * the source for the application under test is under %APP\_DIR%
  * a virtual device (AVD) is running or a physical device is connected to the computer

If you need more internal storage (the Gui Tree and the coverage data will be saved there), you must run the emulator from command line:

```
emulator @VirtualDeviceName -partition-size XXXX
```

where XXXX is the size of the storage in MB.

# Preparation #

The following steps are required in order to run the test:

  * the file `Resources.java` has the application specific parameters
  * the `Android Manifest` is updated with the correct target package for the instrumentation %APP\_PACKAGE%
  * a version of **Robotium** JAR is in the _libs_ subdir of the crawler
  * the `androidtest.jar` has been updated to the last version and copied in the _libs_ subdir of the crawler

# Update Project #

To create the project build and properties file, open the system shell and run the following commands::

```
cd %APP_DIR%
android update project --path .
cd %CRAWLER_DIR%
android update test-project -p %CRAWLER_DIR% --main %APP_DIR%
```

# Build and Run #

In order to build the .apk for both the app and the crawler, install them on the device and run the test, open the system shell and run the following command:

```
ant emma debug install test
```

The coverage report will be automatically generated in the current directory.

# Troubleshooting #

## App Initialization ##

If the app needs to be initialized (putting user and pass in Wordpress, download a bible in AndBible, ...) and you can't or won't do it via code, run:

```
ant emma debug install
```

from the crawler directory, open the app, perform the preliminary tasks, and then run:

```
ant emma test
```

## Target Failure ##

When the install and/or the test target fails, follow these steps.

### Build ###

In order to build the .apk for both the app and the crawler, open the system shell and run the following commands:

```
ant emma debug
```

### Run ###

In order to install both packages on the device, just use the following:

```
adb install -r %APP_DIR%\%APP_NAME%-instrumented.apk
adb install -r %CRAWLER_DIR%\%APP_NAME%Test-debug.apk
```

Then, run the test with the coverage flag on true:

```
adb shell am instrument -w -e coverage true com.nofatclips.crawler/android.test.InstrumentationTestRunner
```

Open another shell and run one of the following:

```
adb logcat *:I
adb logcat nofatclips:I *:E
```

to read the log. (Remove the filter for the whole thing!)

### Report Generation ###

Emma will generate the coverage.ec file on the device under `/data/data/%APP_PACKAGE%/files/coverage.ec`

(the actual location will be shown in the adb console.)

Pull it from the device into the crawler directory:

```
adb pull /data/data/%APP_PACKAGE%/files/coverage.ec %CRAWLER_DIR%
```

then use the following command to generate an HTML report:

```
java -cp lib\emma_device.jar emma report -r html -in %CRAWLER_DIR%\coverage.em,%CRAWLER_DIR%\coverage.ec -sp %APP_DIR%\src
```

You can add as many metadata (.em) and data (.ec) files as needed, just separate the file names with a comma.

The report can now be found at `%CRAWLER_DIR%\coverage\` (the actual location will be shown in the console.)