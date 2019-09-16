package com.example.workcalendar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import com.example.workcalendar.DAO.DB;
import com.example.workcalendar.Entity.Users;
import com.example.workcalendar.RxJavaValidation.RxTextEditor;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function3;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Add_User extends AppCompatActivity {

    private Toolbar toolbar;
    private boolean isEdit;
    private ImageButton accept;
    private EditText fio_text,brth_date_text, phone_text;
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

        if(user_id>0 & isEdit)
        {
            Load_user(user_id);
        }
        Observable<String> fioObservable= RxTextEditor.getObservableTextValidation(fio_text);
        Observable<String> phoneObservable=RxTextEditor.getObservableTextValidation(phone_text);
        Observable<Boolean> birtDateObservable=RxTextEditor.getObservableBirthDayValidation(brth_date_text);
        Observer<Object> observer=new Observer<Object>() {
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
    private void Load_user(int user_id)
    {
         Users user= DB.getUser(user_id);
        fio_text.setText(user.getName());
        phone_text.setText(user.getPhone());
        Date date=user.getDateOfBirth();
        SimpleDateFormat format=new SimpleDateFormat("d.M.Y");
        brth_date_text.setText(format.format(date));
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
        SimpleDateFormat format=new SimpleDateFormat("d.M.Y");

        return format.parse(date);
    }

    public void onClick(View v) throws ParseException {
        Intent intent=new Intent();
        if(v.getId()==R.id.users_add_accepr_button)
        {

            Date date=Parse_date(brth_date_text.getText().toString());
            //long date=Long.getLong(brth_date_text.getText().toString());
            Users user=new Users();
            user.setName(fio_text.getText().toString());
            user.setDateOfBirth(date);
            user.setPhone(phone_text.getText().toString());

            if(isEdit)
            {
                user.setId(user_id);
                intent.putExtra("type",true);
                DB.updateUser(user);
            }else
            {
                intent.putExtra("type",false);
                DB.insertUser(user);
            }
            setResult(RESULT_OK,intent);
        }else
        {
            setResult(RESULT_CANCELED);
        }
        finish();
    }
}
