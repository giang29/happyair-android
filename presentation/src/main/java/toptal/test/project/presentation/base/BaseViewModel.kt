package toptal.test.project.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

interface BaseViewState

abstract class BaseViewModel<VS : BaseViewState> : ViewModel() {

    protected val disposables = CompositeDisposable()

    protected val _viewStates = MutableLiveData<VS>()
    val viewStates: LiveData<VS>
        get() = _viewStates

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }
}
