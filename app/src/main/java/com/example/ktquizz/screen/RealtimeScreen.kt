package com.example.ktquizz.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.example.ktquizz.firebaseRealtimeDb.viewmodel.RealTimeViewModel
import com.example.ktquizz.module.RealtimeModel
import com.example.ktquizz.utils.ResultState
import com.example.ktquizz.utils.showMsg
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@Composable
fun RealtimeScreen(
    isInsert: MutableState<Boolean>,
    viewModel: RealTimeViewModel= hiltViewModel()
) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val name = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }
    val scope= rememberCoroutineScope()
    val context= LocalContext.current
    val isDialog=remember { mutableStateOf(false)}
    val res=viewModel.res.value
    val isUpdate=remember { mutableStateOf(false)}
    val isDelete=remember { mutableStateOf(false)}
    if (isInsert.value) {
        AlertDialog(
            onDismissRequest = {
                isInsert.value = false
            },
            text={
                 Column(
                     Modifier
                         .fillMaxWidth()
                         .padding(10.dp),
                     horizontalAlignment=Alignment.CenterHorizontally
                 ) {
                     TextField(value =name.value , onValueChange ={
                         name.value=it},
                         placeholder =
                         {
                             Text(text = "name")
                         } )
                     Spacer(modifier = Modifier.height(10.dp))
                     TextField(value =email.value , onValueChange ={
                         email.value=it},
                         placeholder =
                         {
                            Text(text = "Email")
                         } )
                     Spacer(modifier = Modifier.height(10.dp))
                     TextField(value =password.value , onValueChange ={
                         password.value=it},
                         placeholder =
                         {
                             Text(text = "password")
                         } )
                     Spacer(modifier = Modifier.height(10.dp))
                     TextField(value =confirmPassword.value , onValueChange ={
                         confirmPassword.value=it},
                         placeholder =
                         {
                             Text(text = "confirmPassword")
                         } )


                 }
            }
            ,
            confirmButton = {
               Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center)
                {
                    Button(onClick = {
                        scope.launch(Dispatchers.Main) {
                            viewModel.insert(
                                RealtimeModel.RealtimeUsers(
                                    name = name.value,
                                    email = email.value,
                                    password = password.value,
                                    coins = 0
                                )
                            ).collect{
                                when(it) {
                                    is ResultState.Success->{
                                        context.showMsg(msg=it.data)
                                        name.value=""
                                        email.value=""
                                        password.value=""
                                        confirmPassword.value=""
                                        isDialog.value=false
                                        isInsert.value=false
                                    }
                                    is ResultState.Failure->{
                                        context.showMsg(msg=it.msg.toString())
                                        isDialog.value=false
                                    }
                                    is ResultState.Loading->{
                                        isDialog.value=true
                                    }
                                }
                            }
                        }
                    }) {
                        Text(text = "Sign Up")
                    }
                }
            }
        )
    }
    if(isUpdate.value)
    {
        update(isUpdate = isUpdate, userState =viewModel.updateRes.value, viewModel =viewModel )
    }
    if(res.user.isNotEmpty()) {
        LazyColumn{
            items(res.user, key ={
                it.key!!
            }) {res->
               EachRow(userState = res.user!!,
                   onUpdate = {
                       isUpdate.value= true
                       viewModel.setData(res)
                   },
                   onDelete = {
                       isDelete.value = true
                       if(isDelete.value)
                       {
                           scope.launch (Dispatchers.Main){
                       viewModel.delete(res.key!!).collect{
                           when(it) {
                               is ResultState.Success->{
                                   context.showMsg(msg=it.data)
                                   isDialog.value=false
                               }
                               is ResultState.Failure->{
                                   context.showMsg(msg=it.msg.toString())
                                   isDialog.value=false
                               }
                               is ResultState.Loading->{
                                  isDialog.value=true
                               }
                           }
                       }
                   }
                       }
                       isDelete.value=false
                   }
               )
            }
        }
    }

    if(res.loading)
    {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Center){
            CircularProgressIndicator()
        }
    }
    if(res.error.isNotEmpty()){
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Center){
           Text(text = res.error)
        }
    }
}
@Composable
fun EachRow(
    userState:RealtimeModel.RealtimeUsers,
    onDelete: () -> Unit={},
    onUpdate: () -> Unit={}
)
{
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        )
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onUpdate()
            })
        {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Row (
                    modifier = Modifier
                     .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,

                ){
                    Text(text ="Hii ${userState.name}",
                        style=TextStyle(
                            color = Color.Black,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold )
                        )
                    IconButton(onClick = {
                                    onDelete()
                    },modifier=Modifier.align(CenterVertically)) {
                        Icon(Icons.Default.Delete, contentDescription = "", tint = Color.Red)
                    }

                }
                Text(text =userState.email!!,
                    style=TextStyle(
                        color = Color.Gray,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal )
                )
                Text(text =userState.password!!,
                    style=TextStyle(
                        color = Color.Gray,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal )
                )
                Text(text ="${userState.coins}",
                    style=TextStyle(
                        color = Color.Red,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal )
                )
            }
        }
    }
}
@Composable
fun update(
    isUpdate:MutableState<Boolean>,
    userState: RealtimeModel,
    viewModel: RealTimeViewModel
)
{
    val email = remember { mutableStateOf(userState.user?.email) }
    val password = remember { mutableStateOf(userState.user?.password) }
    val name = remember { mutableStateOf(userState.user?.name) }
    val scope= rememberCoroutineScope()
    val context= LocalContext.current
    if(isUpdate.value)
    {
        AlertDialog(
            onDismissRequest = {
                isUpdate.value = false
            },
            text={
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalAlignment=Alignment.CenterHorizontally
                ) {
                    TextField(value =name.value!! , onValueChange ={
                        name.value=it},
                        placeholder =
                        {
                            Text(text = "name")
                        } )
                    Spacer(modifier = Modifier.height(10.dp))
                    TextField(value =email.value!!, onValueChange ={
                        email.value=it},
                        placeholder =
                        {
                            Text(text = "Email")
                        } )
                    Spacer(modifier = Modifier.height(10.dp))
                    TextField(value =password.value!! , onValueChange ={
                        password.value=it},
                        placeholder =
                        {
                            Text(text = "password")
                        } )
                    Spacer(modifier = Modifier.height(10.dp))



                }
            }
            ,
            confirmButton = {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center)
                {
                    Button(onClick = {
                        scope.launch(Dispatchers.Main) {
                            viewModel.update(
                                RealtimeModel(
                                    user = RealtimeModel.RealtimeUsers(
                                        name = name.value,
                                        email = email.value,
                                        password = password.value,
                                        coins = 0
                                    ),key = userState.key
                                )
                            ).collect{
                                when(it) {
                                    is ResultState.Success->{
                                        context.showMsg(msg=it.data)
                                        isUpdate.value=false

                                    }
                                    is ResultState.Failure->{
                                        context.showMsg(msg=it.msg.toString())

                                    }
                                    is ResultState.Loading->{

                                    }
                                }
                            }
                        }
                    }) {
                        Text(text = "update")
                    }
                }
            }
        )
    }
}
