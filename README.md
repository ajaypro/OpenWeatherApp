# OpenWeatherApp
simple app using openweather api to fetch weather of current location.

Tech
----

* Kotlin - programming language
* Retrofit - network call
* Coroutines - running Background tasks
* Glide - Image loading 
* WorkManager - schedule network call every 1 hour 

Design Pattern
--------------

MVVM - Overall app architecture
Repository Design pattern - Data been fetched either from network or db which is taken care by repository

App
---
* In main activity app checks for network connection and GPS enabled if not GPS enabling permission is requested
* In the `OnPermssionResult()` once permission is granted viewmodel starts
* Current location is been observed from location repository's livedata observed in viewmodel and starts the network call.
* Get the current location using device's GPS or internet and fetches weather of the location. 
* first call to fetch data will get data from server and saves in db and this data is served to ui through
  viewmodel with livedata as db `get()` return livedata. 
  
  
  To Do
  -----
  
  * Dagger implementation for workmanager
  * Testcases



