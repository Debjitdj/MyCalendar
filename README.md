# My Calendar App

This is has my old android project and React-native settings page.

This readme is not complete and currently only for my personal use. If you are reading this, it's possible these steps are not going to be enough for you to run this project locally.

### Run it locally

Mainly need 2 terminal windows to run it in a physical device, and 1 for a virtual device

For a physical device, do these:
```
$ lsusb
```

find the device , for example `Bus 002 Device 060: ID 2717:ff48`
Take the first 4 numbers from the ID and do this:
```
$ echo 'SUBSYSTEM=="usb", ATTR{idVendor}=="2717", MODE="0666", GROUP="plugdev"' | sudo tee /etc/udev/rules.d/51-android-usb.rules
```

Now let the device access the local port
```
$ adb reverse tcp:8081 tcp:8081
```

Now make sure it finds your device by doing this:
```
$ adb devices
```

For a virutal device, start from here:
Run the react native component from a terminal window:
```
$ react-native start -- --reset-cache
```

From android studio run the android app. If using a virtual device, make sure to start the virutal device earlier before running the app. This makes the running process much faster.
