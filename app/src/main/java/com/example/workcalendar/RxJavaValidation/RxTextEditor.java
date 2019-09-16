package com.example.workcalendar.RxJavaValidation;

import android.support.v4.util.Pair;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.EditText;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RxTextEditor {

    public static Observable<String> getObservableTextValidation(@NonNull final EditText editText){
        final PublishSubject<String> subject=PublishSubject.create();
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                subject.onNext(s.toString());
            }
        });
        return subject;
    }
    public static Observable<Boolean> getObservableBirthDayValidation(final EditText editText)
    {
        Observable<Boolean> observable=Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(final ObservableEmitter<Boolean> emitter) throws Exception {
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        SimpleDateFormat format=new SimpleDateFormat("d/M/Y");
                        String sDate=s.toString();
                        if(!sDate.isEmpty() & sDate.length()>=10) {
                            try {
                                Date date = format.parse(sDate);
                                emitter.onNext(validateDate(date));
                            } catch (ParseException e) {
                                emitter.onError(e);
                            }
                        }else
                        {
                            emitter.onNext(false);
                        }
                    }
                });
            }
        });
                return observable;
    }

    private static Boolean validateDate(Date birthDate)
    {
        Date currentDate=new Date(System.currentTimeMillis());
        if(currentDate.before(birthDate) | currentDate.equals(birthDate))
        {
            return false;
        }else
        {
            return true;
        }
    }

    public static Observable<Pair<Date,String>> getObservableWorkDay(final CalendarView calendarView)
    {
        Observable<Pair<Date,String>> observable=Observable.create(new ObservableOnSubscribe<Pair<Date,String>>() {
            @Override
            public void subscribe(final ObservableEmitter<Pair<Date,String>> emitter) throws Exception {
                 calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                     @Override
                     public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                         SimpleDateFormat format=new SimpleDateFormat("dd.MM.yyyy");
                         String sDate=String.valueOf(dayOfMonth)+"."+String.valueOf(month+1)+"."+String.valueOf(year);
                         Date workDate = new Date();
                         try {
                             workDate = format.parse(sDate);

                         } catch (ParseException e) {

                         }
                         Pair<Date, String> result;
                        if (checkWorkDate(workDate)){
                           result= new Pair<>(workDate,"");

                        }else {
                            result=new Pair<>(workDate,"Дата меньше текущей");
                        }
                        emitter.onNext(result);
                     }
                 });
            }
        });        return observable.subscribeOn(Schedulers.io());
    }
    private static boolean checkWorkDate(Date workDate)
    {
        Date currentDate=new Date(System.currentTimeMillis());
        currentDate.setHours(0);
        currentDate.setMinutes(0);
        currentDate.setSeconds(0);
        if(workDate!=null & workDate.after(currentDate)) {

         return true;
        }else{
            return false;
        }
    }
}
