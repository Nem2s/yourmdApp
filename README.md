# yourmdApp!

This file is a brief description of the project architectural's choices, as well as comments regarding the code, and a short description of unit tests.

The main goal of the app is to let the user be able to search for a venue by typing in the search field a name.


# Architecture

The design pattern chosen to develop the app is the *MVVM (Model View ViewModel)*: it allows you to save and restore the internal lifecycle of the Activity without any *boilerplate* code using the **ViewModel** class from the Android support package. The main difference with the MVP architecture is that there isn't any *Contract* interface between the View (*Activity*) and the Controller (*ViewModel*): every-time the data is updated in the ViewModel class, the Activity is notified through **Observers**. In the ViewModel class there are three observers relatives to the three main views of the activity:

 - **Venues List Observer**: Whenever the API call is invoked the *Mutable* list is updated with the new values received from the FourSquare server; the Activity is subscribed to this observer in order to reload the items in the adapter object and update the UI accordingly.
 - **Error String Observer**: It is responsible for showing the user whenever an error occurs, using a TextView field in the activity UI.
 - **ProgressBar View Observer**: It appears whenever there's a network request to show the user that the UI is waiting for a response from the backend side.

All the three values are updated during the API call, according to the current status of the call itself. Simply by calling the `.postValue` method of the Observer object the Activity is aware of how to handle the UI without even knowing about the API call. The ViewModel class is scoped to the activity's lifecycle passed to the *ViewModelProvider* when getting the ViewModel: it means that even if the Activity changes the current lifecycle's status the ViewModel will never be instantiated again, it will be destroyed when the Activity is *finished*. This results in a perfect scenario for when the user rotates the device: the activity is destroyed and re-created again to match the orientation of the device but the ViewModel will survive to this and there's no need to recall the Repository every time the activity is recreated.

## Code

This section provides a short description for each class involved in the architectural design of the app:

    public class SearchRepository {  
      private static Retrofit instance;  
      private static final String URL = "https://api.foursquare.com/";

    public interface myAPI {  
      
      @GET("v2/venues/search?client_id=...&client_secret=...&v=20181009")  
      Call<SearchVenueResponse> searchVenue(@Query("near") String searchVenueRequest  
      );  
    }

It is responsible to make network requests to the *FourSqure* backend service. It consists of a *Instance* of the Retrofit client, which is a *Singleton* object, and a *Interface* in which all the API calls prototypes are defined.
The `searchVenue` call returns a *json* object which is parsed using the class `SearchVenueResponse`.

    public class MainActivityViewModel extends ViewModel{  
      private MutableLiveData<List<Venue>> venues = new MutableLiveData<>();  
      private MutableLiveData<String> error = new MutableLiveData<>();  
      private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();  
      private SearchRepository.myAPI api = SearchRepository.getRetrofitInstance().create(SearchRepository.myAPI.class);   

The ViewModel class inherits from the *abstract* class *ViewModel* and it provides the three *Observers* (which are defined by using the **LiveData** object ) and an instance of the Repository object.

    public LiveData<List<Venue>> OnVenuesChanged() {  
      return venues;  
    }  
      
    public LiveData<String> OnErrorOccured() {  
      return error;  
    }  
      
    public LiveData<Boolean> OnLoadingStatus() {  
      return isLoading;  
    }

It also offers the *getters* for the three Observers specifying the behaviour for each object .

    public class MainActivity extends AppCompatActivity {  
      
      @BindView(R.id.recycler_view)  
      RecyclerView recyclerView;  
      @BindView(R.id.til_search)  
      TextInputLayout searchLayout;  
      @BindView(R.id.tiet_search)  
      TextInputEditText et_search;  
      @BindView(R.id.btn_search)  
      MaterialButton searchButton;  
      @BindView(R.id.loading)  
      ProgressBar loading;  
      @BindView(R.id.error_message)  
      TextView errorTv;
      ...

The *MainActivity* file contains the *View* of the architecture, it uses **Butterknife** for the *DataBinding* process.

        void observeData() {  
          model = ViewModelProviders.of(this).get(MainActivityViewModel.class);
          model.OnVenuesChanged().observe(this, venues -> { 
          ...
          ...
          }
          model.OnErrorOccured().observe(this, error -> {
          ...
          ...
          }
          model.OnLoadingStatus().observe(this, isLoading -> {
          ...
          ...
          }
        }
Using `observeData()` the activity is able to initialise the ViewModel using the static method of the ViewModelProviders class and starting observing the data changing.
The activity also contains the `adapter` object to manage the `recyclerview`.



## Unit Test

To be able to perform unit tests on the ViewModel class I managed to *mock* the `LifecycleOwner` object using a specific class in the *TestHelper* folder.
Mocking the owner makes possible to test the results in the observer callback whenever the data changes. To perform the test on the Retrofit call I managed to handle the asynchronous calls using a low-level synchronisation constructor: `CountDownLatch`.
Another test is present, this includes a UI test to make sure the behaviour of all the UI objects is correct in some scenarios.
