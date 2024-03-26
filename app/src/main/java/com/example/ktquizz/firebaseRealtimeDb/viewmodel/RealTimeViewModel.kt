package com.example.ktquizz.firebaseRealtimeDb.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ktquizz.firebaseRealtimeDb.repository.RealtimeRepository
import com.example.ktquizz.module.RealtimeModel
import com.example.ktquizz.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RealTimeViewModel @Inject constructor(
    private val repo: RealtimeRepository
):ViewModel() {
    private val _res:MutableState<UserState> = mutableStateOf(UserState())
    val res:State<UserState> = _res
    fun insert(users: RealtimeModel.RealtimeUsers)=repo.insert(users)
    private val _updateRes:MutableState<RealtimeModel> = mutableStateOf(RealtimeModel(
        user=RealtimeModel.RealtimeUsers(),
    )
    )
    val updateRes:State<RealtimeModel> = _updateRes
    fun setData(data:RealtimeModel)
    {
        _updateRes.value=data
    }
    init {
        viewModelScope.launch {
            repo.getAll().collect{
                when(it){
                    is ResultState.Success->{
                        _res.value= UserState(it.data)
                    }
                    is ResultState.Failure->{
                        _res.value= UserState(error=it.msg.toString())
                    }
                    is ResultState.Loading->{
                        _res.value= UserState(loading=true)
                    }
                }
            }
        }
    }
    fun delete(key:String) = repo.delete(key)
    fun update(users: RealtimeModel) = repo.update(users)
    fun get(key:String) = repo.get(key)
    fun getAll():Flow<ResultState<List<RealtimeModel>>> = repo.getAll()
    fun deleteAll():Flow<ResultState<String>> = repo.deleteAll()

}
data class UserState(
    val user:List<RealtimeModel> = emptyList(),
    val error:String="",
    val loading:Boolean=false

)