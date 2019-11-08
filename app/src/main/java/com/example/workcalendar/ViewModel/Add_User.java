package com.example.workcalendar.ViewModel;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import com.example.workcalendar.DataModel.Entity.Users;
import com.example.workcalendar.GlobalApplication;
import com.example.workcalendar.R;
import com.example.workcalendar.ViewModel.RxJavaValidation.RxTextEditor;
import com.jakewharton.rxbinding3.widget.RxTextView;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function3;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Add_User extends AppCompatActivity implements ActivityModel {

    private Toolbar toolbar;
    private boolean isEdit;
    private ImageButton accept;
    private EditText fio_text,brth_date_text, phone_text;
    Observer<Object> observer;
    private int user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__user);
        toolbar=(Toolbar) findViewById(R.id.users_add_toolbar);
        setSupportActionBar(toolbar);
        init();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void loadList(ArrayList<Users> list) {

    }
    @Override
    public void load(Object oneUser)
    {
        Users user=(Users)oneUser;
        fio_text.setText(user.getName());
        phone_text.setText(user.getPhone());
        Date date=user.getBirthDate();
        SimpleDateFormat format=new SimpleDateFormat("dd.MM.yyyy");
        brth_date_text.setText(format.format(date));
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void init()
    {
        fio_text=findViewById(R.id.users_add_fio_edittext);
        brth_date_text =findViewById(R.id.users_add_bdate_edittext);
        phone_text=findViewById(R.id.users_add_phone_edittext);
        Bundle bundle=getIntent().getExtras();
        isEdit=bundle.getBoolean("isEdit");
        user_id=bundle.getInt("User_id");
        accept =findViewById(R.id.users_add_accepr_button);
        GlobalApplication.getPresenter().setActivityModel(this);
        if(user_id>0 & isEdit)
        {
            GlobalApplication.getPresenter().loadUser(user_id);
        }
        Observable<String> fioObservable= RxTextView.afterTextChangeEvents(fio_text).flatMap((textViewAfterTextChangeEvent)->{
                return Observable.just(textViewAfterTextChangeEvent.component2().toString());
        });
        Observable<String> phoneObservable=RxTextEditor.getObservableTextValidation(phone_text);
        Observable<Boolean> birtDateObservable=RxTextEditor.getObservableBirthDayValidation(brth_date_text);
        observer=new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Object o) {
                Boolean result=(Boolean)o;
                accept.setEnabled(result);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        Observable.combineLatest(fioObservable, phoneObservable, birtDateObservable, new Function3<String, String, Boolean, Object>() {
            @Override
            public Object apply(String s, String s2, Boolean aBoolean) throws Exception {
                if(!s.isEmpty() & !s2.isEmpty() & aBoolean) {
                    return true;
                }else
                {
                    return false;
                }
            }
        }).subscribe(observer);
    }
    @Override
    public  Object getData(){
        Date date= null;
        try {
            date = Parse_date(brth_date_text.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Users user=GlobalApplication.workComponent().getUser();
        user.setName(fio_text.getText().toString());
        user.setBirthDate(date);
        user.setPhone(phone_text.getText().toString());
        if(isEdit){user.setId(user_id);}
        else{ user.setId(0);}
        return  user;
    }
    public static java.util.Date Parse_date(String value) throws ParseException {
        String date;
        if (value.contains("-"))
        {
            date=value.replace("-",".");
        }else if(value.contains("/"))
        {
            date=value.replace("/",".");
        }else
        {
            date=value;
        }
        SimpleDateFormat format=new SimpleDateFormat("dd.MM.yyyy");

        return format.parse(date);
    }

    public void onClick(View v) throws ParseException {
        Intent intent=new Intent();
        if(v.getId()==R.id.users_add_accepr_button)
        {
            GlobalApplication.getPresenter().acceptUser();
            setResult(RESULT_OK,intent);
        }else
        {
            setResult(RESULT_CANCELED);
        }
        finish();
    }
}
