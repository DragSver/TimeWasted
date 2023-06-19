package com.example.timewaster.viewmodel

import android.content.Context
import android.content.SharedPreferences
import android.widget.ImageButton
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.timewaster.Activity
import com.example.timewaster.DaggerAppComponent
import com.example.timewaster.R
import com.example.timewaster.usecase.UseCase
import com.google.gson.Gson
import retrofit2.Response
import javax.inject.Inject

class ActivityViewModel : ViewModel() {

    lateinit var response: Response<Activity>
    var activityLiveData = MutableLiveData<Activity>()

    var sharedPreferences : SharedPreferences? = null
    var jsonActivities: String? = null
    var activitiesLiveData = MutableLiveData<MutableList<Activity>>()

    @Inject lateinit var useCase: UseCase

    fun init(context: Context) {
        DaggerAppComponent.builder().build().inject(this)

        activitiesLiveData.value = mutableListOf()
        sharedPreferences = context.getSharedPreferences("Preferences", Context.MODE_PRIVATE)
        if (sharedPreferences != null && !sharedPreferences!!.contains("activities")) {
            putPreferences()
        } else {
            getPreferences()
        }
    }

    fun switchFavorite(activity: Activity, button: ImageButton) {
        if (activitiesLiveData.value!!.contains(activity)){
            activitiesLiveData.value!!.remove(activity)
            button.setImageResource(R.drawable.baseline_favorite_border_24)
        } else {
            activitiesLiveData.value!!.add(activity)
            button.setImageResource(R.drawable.baseline_favorite_24)
        }
        putPreferences()
    }

    fun getPreferences() {
        jsonActivities = sharedPreferences?.getString("activities", null)
        activitiesLiveData.value = Gson().fromJson(jsonActivities, Array<Activity>::class.java).toMutableList()
    }

    fun putPreferences() {
        jsonActivities = Gson().toJson(activitiesLiveData.value)
        sharedPreferences?.edit()?.putString("activities", jsonActivities)?.apply()
    }

    fun setFavoriteButton(button: ImageButton, activity: Activity) {
        if (activitiesLiveData.value!!.contains(activity)) {
            button.setImageResource(R.drawable.baseline_favorite_24)
        } else {
            button.setImageResource(R.drawable.baseline_favorite_border_24)
        }
    }

    suspend fun getData(): Activity? {
        response = useCase.getRandomActivity()
        activityLiveData.value = response.body()!!
        return activityLiveData.value
    }
}