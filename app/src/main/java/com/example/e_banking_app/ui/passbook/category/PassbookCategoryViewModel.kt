package com.example.e_banking_app.ui.passbook.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.e_banking_app.R
import com.example.e_banking_app.data.repository.PassbookRepository

class PassbookCategoryViewModel(private val passbookRepository: PassbookRepository) : ViewModel() {

    private val _passbookCategoryListState = MutableLiveData<PassbookCategoryListState>()
    val passbookCategoryListState: LiveData<PassbookCategoryListState> = _passbookCategoryListState

    fun fetchPassbookCategoryList() {
        passbookRepository.getPassbookCategoryList(
            onSuccess = {
                _passbookCategoryListState.value = PassbookCategoryListState(success = it)
            },
            onFailure = {
                _passbookCategoryListState.value =
                    PassbookCategoryListState(error = R.string.error_fail_to_fetch_passbook_category_list)
            }
        )
    }
}


//private var _binding: FragmentPassbookCategoryBinding? = null
//private val binding get() = _binding!!
//
//private lateinit var viewModel: PassbookCategoryViewModel
//
//override fun onCreateView(
//    inflater: LayoutInflater, container: ViewGroup?,
//    savedInstanceState: Bundle?
//): View {
//    viewModel = PassbookCategoryViewModel(passbookRepository = PassbookRepository(context!!))
//    _binding = FragmentPassbookCategoryBinding.inflate(inflater, container, false)
//    return binding.root
//}
//
//override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//    super.onViewCreated(view, savedInstanceState)
//
//    init()
//
//
//}
//
//private fun init(){
//    viewModel.fetchPassbookCategoryList()
//}
