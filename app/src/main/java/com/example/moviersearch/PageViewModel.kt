    package com.example.moviersearch

    import androidx.lifecycle.LiveData
    import androidx.lifecycle.MutableLiveData
    import androidx.lifecycle.ViewModel

    class PageViewModel : ViewModel() {
        val favoriteItems = mutableListOf<MovieData>()
        private val _currentPage = MutableLiveData<Int>()
        val currentPage: LiveData<Int> get() = _currentPage

        private val _shhhhRequitre = MutableLiveData<String>()
        val selectedRequitre: LiveData<String> get() = _shhhhRequitre

        private val _year = MutableLiveData<Int>()
        val year: LiveData<Int> get() = _year

        init {
            _currentPage.value = 1
        }
        fun setCurrentPage(page: Int) {
            _currentPage.value = page
        }
        fun setSearchQuery(query: String) {
            _shhhhRequitre.value = query
        }
        fun setYear(year: Int) {
            if (_year.value != year) {
                _year.value = year
            } else {
                _year.value = year
            }
        }
    }