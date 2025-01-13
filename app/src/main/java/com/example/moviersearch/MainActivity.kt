package com.example.moviersearch

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.moviersearch.databinding.ActivityMainBinding
import com.example.moviersearch.databinding.DrawerHeaderBinding
import com.google.android.material.navigation.NavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val viewModel: PageViewModel by lazy {
        ViewModelProvider(this).get(PageViewModel::class.java)
    }
    var fifi = 0
    private lateinit var selectedButtonuk: Button
    private lateinit var selectedButton: Button
    private lateinit var selectedButton1: Button

    private lateinit var selectedButton2: Button

    private lateinit var selectedButton3: Button

    private lateinit var selectedButton4: Button

    private lateinit var binding2: DrawerHeaderBinding
    private lateinit var binding: ActivityMainBinding
    private lateinit var navHostFragment: NavHostFragment
    private var selected = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding2 = DrawerHeaderBinding.inflate(layoutInflater)
        if (!isInternetAvailable()) {
            setContentView(R.layout.withoutinternet)
            return // Завершуємо, якщо інтернет недоступний
        }
        setContentView(binding.root)

        navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        val navController = navHostFragment.navController

    }



    private fun isInternetAvailable(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        return capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    private fun enableImmersiveMode() {
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or  // Приховує нижню панель
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY   // Залишає прихованою після взаємодії
                )
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            enableImmersiveMode() // Підтримка імерсивного режиму при поверненні фокусу
        }
    }

    fun NormalYear(view: View) {
        viewModel.setYear(0)
        binding.drawerLayout.closeDrawer(GravityCompat.START)
    }

    fun SearchYear(view: View) {
        val editTextik = findViewById<EditText>(R.id.editTextText2)
        val text = editTextik.text.toString()

        if (text.isNotEmpty()) {
            try {
                val year = text.toInt()
                viewModel.setYear(year)
            } catch (e: NumberFormatException) {
                Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Year field is empty", Toast.LENGTH_SHORT).show()
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
    }



    fun Mama(view: View) {
        if(fifi == 0 ){
            selectedButtonuk = findViewById(R.id.buttonNormal)
            ++fifi
        }

        this.selectedButton = findViewById<Button>(R.id.buttonMovie)
        if(selectedButton!=selectedButtonuk) {
            selectedButton.setTextColor(ContextCompat.getColor(this, R.color.black))
            selectedButton.setBackgroundResource(R.drawable.drawable_button_selected)

            selectedButtonuk.setTextColor(ContextCompat.getColor(this, R.color.white))
            selectedButtonuk.setBackgroundResource(R.drawable.drawable_button)
            selectedButtonuk = selectedButton
            viewModel.setSearchQuery(selectedButton.text.toString().lowercase())
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)

    }

    fun Series(view: View) {
        if(fifi == 0 ){
            selectedButtonuk = findViewById(R.id.buttonNormal)
            ++fifi
        }

        this.selectedButton1 = findViewById<Button>(R.id.buttonSeries)

        if(selectedButton1!=selectedButtonuk) {
            selectedButton1.setTextColor(ContextCompat.getColor(this, R.color.black))
            selectedButton1.setBackgroundResource(R.drawable.drawable_button_selected)

            selectedButtonuk.setTextColor(ContextCompat.getColor(this, R.color.white))
            selectedButtonuk.setBackgroundResource(R.drawable.drawable_button)
            selectedButtonuk = selectedButton1
            viewModel.setSearchQuery(selectedButton1.text.toString().lowercase())

        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
    }
    fun Episode(view: View) {
        if(fifi == 0 ){
            selectedButtonuk = findViewById(R.id.buttonNormal)
            ++fifi
        }
        this.selectedButton2 = findViewById<Button>(R.id.buttonEpisode)
        if(selectedButton2!=selectedButtonuk) {


            selectedButton2.setTextColor(ContextCompat.getColor(this, R.color.black))
            selectedButton2.setBackgroundResource(R.drawable.drawable_button_selected)

            selectedButtonuk.setTextColor(ContextCompat.getColor(this, R.color.white))
            selectedButtonuk.setBackgroundResource(R.drawable.drawable_button)

            selectedButtonuk = selectedButton2
            viewModel.setSearchQuery(selectedButton2.text.toString().lowercase())

        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
    }
    fun Game(view: View) {
        if(fifi == 0 ){
            selectedButtonuk = findViewById(R.id.buttonNormal)
            ++fifi
        }
        this.selectedButton3 = findViewById<Button>(R.id.buttonGame)
        if(selectedButton3!=selectedButtonuk) {


            selectedButton3.setTextColor(ContextCompat.getColor(this, R.color.black))
            selectedButton3.setBackgroundResource(R.drawable.drawable_button_selected)

            selectedButtonuk.setTextColor(ContextCompat.getColor(this, R.color.white))
            selectedButtonuk.setBackgroundResource(R.drawable.drawable_button)

            selectedButtonuk = selectedButton3
            viewModel.setSearchQuery(selectedButton3.text.toString().lowercase())

        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
    }
    fun Normal(view: View) {
        if(fifi == 0 ){
            selectedButtonuk = findViewById(R.id.buttonNormal)
            ++fifi
        }
        this.selectedButton4 = findViewById<Button>(R.id.buttonNormal)
        if(selectedButton4!=selectedButtonuk) {


            selectedButton4.setTextColor(ContextCompat.getColor(this, R.color.black))
            selectedButton4.setBackgroundResource(R.drawable.drawable_button_selected)


            selectedButtonuk.setTextColor(ContextCompat.getColor(this, R.color.white))
            selectedButtonuk.setBackgroundResource(R.drawable.drawable_button)

            selectedButtonuk = selectedButton4
            viewModel.setSearchQuery(selectedButton4.text.toString().lowercase())


        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
    }

    fun Saved(view: View) {
        val navController = findNavController(R.id.fragment)
        navController.popBackStack(R.id.mainFragment, false) // Повертаємося на головний фрагмент
        navController.navigate(R.id.favoriteFragment) // Переходимо на потрібний фрагмент
        binding.drawerLayout.closeDrawer(GravityCompat.START)

    }

}



//val call = DataInstance.api.getsData("a")
//
//call.enqueue(object : Callback<DataResponse> {
//    override fun onResponse(call: Call<DataResponse>, response: Response<DataResponse>) {
//        val dataRespons = response.body()
//        if (response.isSuccessful) {
//            if (dataRespons?.Search != null) {
//                println("Знайдено результати (${dataRespons.totalResults}):")
//                dataRespons.Search.forEach { data ->
//                    println("Назва: ${data.Title}, Рік: ${data.Year}, Тип: ${data.Type}")
//                }
//            } else {
//                println("Немає результатів або помилка: ${dataRespons?.Error}")
//            }
//        } else {
//            println("Помилка відповіді: ${response.code()} )")
//        }
//    }
//
//    override fun onFailure(call: Call<DataResponse>, t: Throwable) {
//        println("Помилка під час виконання запиту: ${t.message}")
//    }
//})