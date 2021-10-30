# NNguyen Assignment
An android project app to demonstrate Android knowledge

I write this project with MVVM pattern in Kotlin by Clean Architecture. Using ViewModels and LiveData with Coroutines and Either, inject dependence with Hitl, and call API with Retrofit.

### 
The following diagram shows the structure of this project with 3 layers:
- Presentation
- Domain
- Data

1. UI calls the method from ViewModel.
2. ViewModel executes Use case.
3. Use case can check input validate and call repository to get data
4. Each Repository returns data from a Data Source (Cached or Remote).
5. Information flows back to the UI where we display the list of the forecast.

Code structure:
  + Core: 
   - DI: class using Hitl to provide and dependence
   - Exception: define common exception and base Failure to return in usecase
   - Extension: some useful extension
   - Functional: use Either to return a 2-side class. Left or Right stand for Failure and Data
   - Interactor: base usecase class
   - Platform: base Android class
   - Navigator: class to open screen
  + Feature: every feature in the app will be separated in each folder
   - Domain: holding model class, usecase, failure, and contract of the repository
   - Repository: holding 2 data sources: local data source and remote data source. The repository implementation will choose data that come from which source. The entity will be defined in this folder. API will be called by retrofit
  + Secrets: class to load and call the native library to decrypt the secret API  
   
<br>
<p align="center">
  <img src="https://blog.cleancoder.com/uncle-bob/images/2012-08-13-the-clean-architecture/CleanArchitecture.jpg" width="750"/>
</p>
<br>

### I follow the expected output 

1. Writing with Kotlin codebase
2. Using architecture: MVVM + LiveData
3. Write Unit Test for ViewModel, Domain Layer, Repository Layer, Model Object
4. Tested and can work in expectation
5. Handle API fail and show up with UI View and simple toast
6. Caching by In-Memory Cache, Cache will valid for 1 min, the maximum cache is 1000 cities. These values can configurable
8. Secure Android App
 - Avoid Decompile APK By setting minifyEnabled and proguard. The idea is using proguard but I have some trouble with config so the release mode is not work well
 - Check rooted device and warning by RootBear library, User can continue to use at their own risk
 - Secure network with Https
 - Encryption for sensitive information by hiding API key with Native code by using klaxit.hiddensecrets to Hidden secret key
9. Android accessibility 
 - I set content description for the widget to Talkback can speak out the content
 - I use constraint layout to display widget with aspect ratio and use SP to scale text
10. I create the Entity to get the response from API, and then transform Entity to Model and pass to the UI view 
11. To run the project
  - Using Android studio Arctic Fox 2020.3.1 patch 3
  - Using NDK and CMake to compile the native library 
  - Import project and run
  - In case the project can not run. Using the APK in the Build folder to install and run

