package ServerBLL;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.net.ConnectivityManagerCompat;
import com.example.workcalendar.DataModel.Entity.Users;
import com.example.workcalendar.GlobalApplication;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.*;

import java.util.ArrayList;

public class FireBaseClient {
     DatabaseReference databaseReference;

    public FireBaseClient(){
        databaseReference=FirebaseDatabase.getInstance().getReference("users");
    }
    public void loadUsers()
    {

        databaseReference.orderByChild("phone").equalTo("89273978402").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("WorkCalendar","Start");
                if (dataSnapshot.hasChildren()) {
                    Iterable<DataSnapshot> users = dataSnapshot.getChildren();
                    for (DataSnapshot data : users) {
                        Log.d("WorkCalendar",data.getKey()+" : "+data.getValue().toString());
                    }
                }
                if(dataSnapshot.getValue()!=null)
                {
                    Log.d("WorkCalendar",dataSnapshot.getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public boolean checkWifi()
    {
        ConnectivityManager connectivityManager=(ConnectivityManager) GlobalApplication.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        Network network=connectivityManager.getActiveNetwork();
        if(network!=null)
        {
            NetworkCapabilities nc=connectivityManager.getNetworkCapabilities(network);
            if (nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI))
            {
                return  true;
            }else return false;
        }else return false;

    }
    //Проверка разрешения на доступ к проверке сети
    public boolean checkPermission()
    {
        int permision= ContextCompat.checkSelfPermission(GlobalApplication.getAppContext(), Manifest.permission.ACCESS_NETWORK_STATE);
        if(permision== PackageManager.PERMISSION_GRANTED)
        {
            return true;
        }else
        {
            return false;
        }
    }
}
