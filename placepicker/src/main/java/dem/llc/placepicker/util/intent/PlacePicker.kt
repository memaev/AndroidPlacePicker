package dem.llc.placepicker.util.intent

import android.content.Context
import android.content.Intent
import dem.llc.placepicker.presentation.activity.PlacePickerActivity
import dem.llc.placepicker.util.namespace.API_KEY

class PlacePicker{
    class IntentBuilder{
        private var apiKey : String? = null

        fun setApiKey(apiKey: String) = apply{this.apiKey = apiKey}

        fun build(packageContext: Context) : Intent{
            val intent = Intent(packageContext, PlacePickerActivity::class.java)
            intent.putExtra(API_KEY, apiKey)
            return intent
        }
    }
}