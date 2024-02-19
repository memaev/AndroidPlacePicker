# Place Picker

✅ This is the light-weight and simple for use place picker library for Android.
Written on Kotlin, using Jetpack Compose and Google Maps SDK.

> [!TIP]
> Library is still in development stage. Feel free to contribute 👨‍💻


## Adding library to your project
In order to add library to your project you need to do the following:
- Add `Jitpack` to your `settings.gradle`
- Add dependency to your **app** level `build.gradle`
- Add place picker activity to your `AndroidManifest.xml`

### Adding jitpack
To your `settings.gradle.kts` add Jitpack import:

***Kotlin DSL***
``` 
dependencyResolutionManagement {
    ...
    repositories {
        maven{ url = uri("https://jitpack.io") }
    }
}
```
***Groovy***
```
dependencyResolutionManagement {
    ...
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

### Adding dependency to `build.gradle`
Add the following dependency to app module `build.gradle` file:

***Kotlin DSL***
```
dependencies{
    ...
    implementation("com.github.memaev:AndroidPlacePicker: [current-version]")
}
```

***Groovy***
```
dependencies{
    ...
    implementation 'com.github.memaev:AndroidPlacePicker: [current-version]'
}
```
> [!NOTE]
> Replace [current-version] with the latest version of the library. You can view it in releases tab.


### Add place picker activity to `AndroidManifest.xml`
In order to open new activity for place picking you need to mark it in your manifest file.
To do it go to **manifest -> AndroidManifest.xml** and add new `activity` to your application:

``` xml
<application
 ... >
 
<activity android:name="dem.llc.placepicker.presentation.activity.PlacePickerActivity" />

</application>
```

## How to use
> [!IMPORTANT]
> You need to get your Google Maps API key before, to use Place Picker!
> [How to get api key](https://developers.google.com/maps/documentation/android-sdk/get-api-key)
> [How to add api key to your project](https://developers.google.com/maps/documentation/android-sdk/secrets-gradle-plugin)

1. Register activity result receiver in your activity:
``` kotlin
private val pickerActivityResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){result->
        val address = result.data?.getParcelable<Location>(LOCATION) // Location class is from library's sources
        if (result.resultCode== RESULT_OK && address!=null){
            // Access all information about the location
            val name = address.name?:"Default name"
            val latitude = address.latitude
            val longitude = address.longitude
        }else{
            //Failed
        }
    }
```
2. Call PlacePicker intent builder, provide the Google Maps SDK API key, call build and provide context. Then start the intent with `pickerActivityResult` that was created before:
``` kotlin
val intent = PlacePicker.IntentBuilder()
            .setApiKey("your-api-key")
            .build(this)
pickerActivityResult.launch(intent)
```

> [!IMPORTANT]
>Enjoy using the library and add it to the favourites!
