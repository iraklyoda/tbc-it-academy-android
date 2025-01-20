package com.example.baseproject.contact

import android.util.Log.d
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContactViewModel : ViewModel() {
    private val contactsJsonResponse = """
        [
            {
                "id": 1,
                "image": "https://www.alia.ge/wp-content/uploads/2022/09/grisha.jpg",
                "owner": "გრიშა ონიანი",
                "last_message": "თავის ტერიტორიას ბომბავდა",
                "last_active": "4:20 PM",
                "unread_messages": 3,
                "is_typing": false,
                "laste_message_type": "text"
            },
            {
                "id": 2,
                "image": null,
                "owner": "ჯემალ კაკაურიძე",
                "last_message": "შემოგევლე",
                "last_active": "3:00 AM",
                "unread_messages": 0,
                "is_typing": true,
                "laste_message_type": "voice"
            },
            {
                "id": 3,
                "image": "https://i.ytimg.com/vi/KYY0TBqTfQg/hqdefault.jpg",
                "owner": "გურამ ჯინორია",
                "last_message": "ცოცხალი ვარ მა რა ვარ შე.. როდის იყო კვტარი ტელეფონზე ლაპარაკობდა",
                "last_active": "1:00",
                "unread_messages": 0,
                "is_typing": false,
                "laste_message_type": "file"
            },
            {
                "id": 4,
                "image": "",
                "owner": "კაკო წენგუაშვილი",
                "last_message": "ადამიანი რო მოსაკლავად გაგიმეტებს თანაც ქალი ის დასანდობი არ არი",
                "last_active": "1:00 PM",
                "unread_messages": 0,
                "is_typing": false,
                "laste_message_type": "text"
            }
        ]
    """

    val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val _contacts = MutableLiveData<MutableList<ContactPto>>(
        mutableListOf()
    )
    val contacts: LiveData<MutableList<ContactPto>> get() = _contacts

    private fun getContacts() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                @OptIn(ExperimentalStdlibApi::class)
                val adapter = moshi.adapter<MutableList<ContactPto>>()
                val contactsAdapted: MutableList<ContactPto>? = adapter.fromJson(contactsJsonResponse)
                _contacts.postValue(contactsAdapted?.toMutableList() ?: mutableListOf())
            } catch (e: Exception) {
                d("ContactsException", e.message.toString())
            }
        }
    }

    init {
        getContacts()
    }

}