package com.example.timewaster.viewmodel

import android.content.Context
import android.content.SharedPreferences
import android.widget.ImageButton
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.timewaster.Activity
import com.example.timewaster.DaggerAppComponent
import com.example.timewaster.R
import com.example.timewaster.databinding.MainBinding
import com.example.timewaster.usecase.UseCase
import com.google.gson.Gson
import retrofit2.Response
import javax.inject.Inject

class ActivityViewModel : ViewModel() {

    lateinit var response: Response<Activity>
    var activityLiveData = MutableLiveData<Activity>()

    var mainBinding : MainBinding? = null

    var sharedPreferences : SharedPreferences? = null
    var jsonActivities: String? = null
    var activitiesLiveData = MutableLiveData<MutableList<Activity>>()

    @Inject lateinit var useCase: UseCase

    fun init(context: Context, binding: MainBinding) {
        DaggerAppComponent.builder().build().inject(this)

        mainBinding = binding

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
        if (mainBinding !=null) {
            if (mainBinding!!.checkboxNone.isChecked) {
                response = useCase.getRandomActivity()
            }
            if (mainBinding!!.checkboxKey.isChecked) {
                response = useCase.getByKey(mainBinding!!.searchKey.text.toString())
            }
            if (mainBinding!!.checkboxType.isChecked) {
                var type : String? = null
                if (mainBinding!!.checkboxEducation.isChecked)
                    type = "education"
                if (mainBinding!!.checkboxRecreational.isChecked)
                    type = "recreational"
                if (mainBinding!!.checkboxSocial.isChecked)
                    type = "social"
                if (mainBinding!!.checkboxDiy.isChecked)
                    type = "diy"
                if (mainBinding!!.checkboxCharity.isChecked)
                    type = "charity"
                if (mainBinding!!.checkboxCooking.isChecked)
                    type = "cooking"
                if (mainBinding!!.checkboxRelaxation.isChecked)
                    type = "relaxation"
                if (mainBinding!!.checkboxMusic.isChecked)
                    type = "music"
                if (mainBinding!!.checkboxBusywork.isChecked)
                    type = "busywork"
                if (type != null)
                    response = useCase.getByType(type)
            }
        }
        activityLiveData.value = response.body()!!
        return activityLiveData.value
    }
}