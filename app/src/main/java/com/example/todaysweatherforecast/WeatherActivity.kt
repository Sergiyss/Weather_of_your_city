package com.example.todaysweatherforecast


import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.todaysweatherforecast.base.BaseCompatActivity
import com.example.todaysweatherforecast.mvp.main.WeatherContract
import javax.inject.Inject
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import kotlinx.android.synthetic.main.content_main.*
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState
import com.example.todaysweatherforecast.adapter_rv.WeatherAdapter
import com.example.todaysweatherforecast.alert_dialog.InputDialog
import com.example.todaysweatherforecast.data_weather.Forecastday
import com.example.todaysweatherforecast.data_weather.Weather
import com.example.todaysweatherforecast.fragment.ForecastNextDaysFragment
import com.example.todaysweatherforecast.fragment.ProgressBarFragment
import kotlinx.android.synthetic.main.activity_main.*


class WeatherActivity : BaseCompatActivity(), WeatherContract.View{


    @Inject lateinit var presenter : WeatherContract.Presenter
    @Inject lateinit var mLayoutManager: LinearLayoutManager
    @Inject lateinit var inputDialog: InputDialog
    @Inject lateinit var progressBarFragment: ProgressBarFragment

    private lateinit var mRecyclerview : RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var adapterWeather : WeatherAdapter


    override fun init(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_main)
        App.get().injector.inject(this)

        callProgressBarTheActivity()
        presenter.attach(this)

        main_toolbar.title = getString(R.string.tollbar_title)
        main_toolbar.setTitleTextColor(resources.getColor(R.color.white))
        main_toolbar.setSubtitleTextColor(getResources().getColor(R.color.white));


        if (isOnline(this)) {
            getLocations()
            activateSlidingLayout()
        }else{
            progressBarFragment.errorMessage(getString(R.string.no_Internet))
        }

        setSupportActionBar(main_toolbar)

        inputDialog.initIistenerInputDialog(object : InputDialog.ListenerInputDialog{
            //Добовляем новый город в список
            override fun send(nameCity: String) {
                presenter.addNewCityList(nameCity)
            }

            override fun cancel() {}
        })

    }


    override fun onBackPressed() {
        if (sliding_layout != null && (sliding_layout.getPanelState() === PanelState.EXPANDED
                    || sliding_layout.getPanelState() === PanelState.ANCHORED)) {
            sliding_layout.setPanelState(PanelState.COLLAPSED)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.add_new_city -> {
                inputDialog.showInputDialog(this@WeatherActivity).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun displayWeatherInformation(nameCity: String, response: Weather) {
        contentMainNameCityTextView.text = nameCity
        contentMainWeatherConditionText.text = response.current.condition.text
        contentMainWeatherMinTempTextView.text = "${response.forecast.forecastday[0].day.mintempC} °C"
        contentMainWeatherMaxTempTextView.text = "${response.forecast.forecastday[0].day.maxtempC} °C"
        contentMainWeatherThisTempTextView.text = "${response.current.tempC} °C"

        Glide.with(this).load("http:${response.current.condition.icon}")
            .into(contentMainWeatherIconImageView)

        initForecastNextDaysFragment(response.forecast.forecastday)
        contentMainprogressbarFragment.visibility = View.GONE
    }

    override fun goneProgressBarLinearRV() {
        activityMainLinearLoadRVProgressBar.visibility = View.GONE
        contentMainRv.visibility = View.VISIBLE
    }

    override fun showWeatherAllCities(arr: List<Weather>) {
        mLayoutManager.apply {
            setReverseLayout(true)
            setStackFromEnd(true)
        }
        adapterWeather = WeatherAdapter(arr)
        viewAdapter = adapterWeather

        mRecyclerview = findViewById<RecyclerView>(R.id.contentMainRv).apply {
            adapter = viewAdapter
            layoutManager = mLayoutManager
        }

        mRecyclerview.getAdapter()!!.notifyDataSetChanged()
        goneProgressBarLinearRV()
    }

    private fun callProgressBarTheActivity(){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.contentMainprogressbarFragment, progressBarFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }

    private fun getLocations(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            // Проверка наличия разрешений
            // Если нет разрешения на использование соответсвующих разркешений выполняем какие-то действия
            progressBarFragment.errorMessage(getString(R.string.off_gps))
            requestMultiplePermissions()
        } else {
            showLocation()
        }
    }

    private fun activateSlidingLayout(){
        sliding_layout.addPanelSlideListener(object : SlidingUpPanelLayout.PanelSlideListener{
            override fun onPanelSlide(panel: View?, slideOffset: Float) {
                //Будем показывать rv список доступных городов.
                if(slideOffset == 1.0f){
                    presenter.startLoadAllCityes()
                    contentMainprogressbarFragment.visibility = View.GONE
                }
                Log.i(DEBUG_TAG, "onPanelSlide, offset " + slideOffset);
            }

            override fun onPanelStateChanged(
                panel: View?,
                previousState: SlidingUpPanelLayout.PanelState?,
                newState: SlidingUpPanelLayout.PanelState?
            ) {
                Log.i(DEBUG_TAG, "onPanelStateChanged $newState")
            }

        })

        sliding_layout.setFadeOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                sliding_layout.setPanelState(PanelState.COLLAPSED)
            }
        })
    }

    private fun requestMultiplePermissions() {
        val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
        Permissions.check(this/*context*/, permissions, null/*options*/, null, object : PermissionHandler() {
            override fun onGranted() {
                showLocation()
            }
        })/*rationale*/
    }
    //Получам название города из gps
    private fun showLocation(){
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        presenter.getObtainNameCity(locationManager, true)
    }

    private fun initForecastNextDaysFragment(list: List<Forecastday>){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.forecastNextDaysFragmentContentMain, ForecastNextDaysFragment(list))
            .addToBackStack(null)
            .commit()
    }

    companion object {
        val DEBUG_TAG = "WeatherActivity"
    }
}
