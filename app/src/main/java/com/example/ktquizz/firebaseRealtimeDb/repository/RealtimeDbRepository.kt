package com.example.ktquizz.firebaseRealtimeDb.repository

import com.example.ktquizz.module.RealtimeModel
import com.example.ktquizz.utils.ResultState
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference

import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class RealtimeDbRepository @Inject constructor(
    private val db: DatabaseReference
):RealtimeRepository {

    override fun insert(user: RealtimeModel.RealtimeUsers): Flow<ResultState<String>> = callbackFlow{
        trySend(ResultState.Loading)

        db.push().setValue(
            user
        ).addOnCompleteListener{
            if(it.isSuccessful){
                trySend(ResultState.Success("Data inserted successfully..."))
            }
            }.addOnFailureListener{
                trySend(ResultState.Failure(it))
            }

            awaitClose{
                close()
            }

    }

    override fun getAll(): Flow<ResultState<List<RealtimeModel>>> = callbackFlow{
        trySend(ResultState.Loading)
        val valueEvent=object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val users=snapshot.children.map {
                    RealtimeModel(
                        it.getValue(RealtimeModel.RealtimeUsers::class.java),
                        key=it.key
                    )
                }
                trySend(ResultState.Success(users))
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(ResultState.Failure(error.toException()))
            }

        }
        db.addValueEventListener(valueEvent)
        awaitClose{
            db.removeEventListener(valueEvent)
            close()
        }
    }

    override fun deleteAll(): Flow<ResultState<String>> = callbackFlow{

    }

    override fun delete(key: String): Flow<ResultState<String>> = callbackFlow {
        trySend(ResultState.Loading)
        db.child(key).removeValue().addOnCompleteListener {
            trySend(ResultState.Success("user deleted"))
        }.addOnFailureListener{
            trySend(ResultState.Failure(it))
        }
        awaitClose {
            close()
        }
    }

    override fun get(key: String): Flow<ResultState<RealtimeModel>> = callbackFlow {
        trySend(ResultState.Loading)
        db.child(key).addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user=snapshot.getValue(RealtimeModel.RealtimeUsers::class.java)

            }

            override fun onCancelled(error: DatabaseError) {
                trySend(ResultState.Failure(error.toException()))
            }

        })
        awaitClose {
            close()
        }

    }

    override fun update( res: RealtimeModel): Flow<ResultState<String>> =
        callbackFlow {
        trySend(ResultState.Loading)
            val map=HashMap<String,Any>()
            map["email"]=res.user?.email!!
            map["password"]=res.user?.password!!
            map["name"]=res.user?.name!!
            map["coins"]=res.user?.coins!!
            db.child(res.key!!).updateChildren(map).addOnCompleteListener {
                trySend(ResultState.Success("user updated Successfully"))
            }.addOnFailureListener {
                trySend(ResultState.Failure(it))
            }

            awaitClose {
                close()
            }


    }
}