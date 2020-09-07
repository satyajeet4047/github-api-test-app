# Github-App

An Android app to use open source github apis to fetch information like top repositories,
contributors


#### Things implemented :
* Architecture - MVVM
* the number of top repositories is limited to 20.
* ProgressDialogs shown while loading data.
* Add a comment to the opened image.
* Use of Constraint layouts, RecyclerView, GridLayout.
* Generic error toast shown in case for API failures.



#### The app has following packages:
1. **data**: It contains all the data accessing and manipulating components.
2. **di**: dependency injection related classed.
3. **ui**: View classes along with their corresponding ViewModel.
4. **utils**: Utility classes.



#### Components Used :
* LiveData -Architecture Component
* ViewModel - Architecture Component
* Dagger - Dependency Injection Component
* RxJava & RxAndroid - For asynchronous tasks
* Picasso - To Load images from URL
* Retrofit & OKHTTP - Handles network calls
