# Location_v3


This Android app requires Google Play Services 8.4.0 and Android 7.23.1.1. 

This app uses a single activity to launch fine-grained location services using the Google Play location API, and stores the current location
along with date and time in the app's memory. The location is updated every 4 seconds. The service runs until destroyed by pressing a 
stop button. Accumulated location data may be viewed by pressing the "Locs" button.

Initiative: 
The text fields for longitude, latitude, and time have been restored to the display of the main activityand are updated with the latest information as it is requested from Google's location services. This allows a constant tracking of the latest positioning information without having to do a button-press to open the entire log, considerably improving the user experience.
