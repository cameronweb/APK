package com.example.workcalendar;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import com.example.workcalendar.DataModel.Entity.Users;
import com.example.workcalendar.ViewModel.ActivityModel;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.*;
import android.widget.*;
import com.example.workcalendar.Presenter.Adapters.Users_adapter;
import com.example.workcalendar.DataModel.DAO.DB;
import com.example.workcalendar.Presenter.Notify.Notify;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Settings extends AppCompatActivity {

    /**
     * The {@link PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = (Toolbar) findViewById(R.id.settings_toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.settings_container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.settings_tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

    }

    public void onFabBackClick(View view)
    {
        if(GlobalApplication.getPresenter().isSettingNotify())
        {
            new Notify().start(true);
        }
        Intent intent =new Intent(Settings.this,MainActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements ActivityModel {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private Switch switchNotify, wifiSwitch;
        private Spinner usersSpiner;
        private static final String ARG_SECTION_NUMBER = "section_number";
        private TimePicker timePicker;

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            int sectionNumber=getArguments().getInt(ARG_SECTION_NUMBER);
            View rootView;
            if (sectionNumber==1) {
                rootView = inflater.inflate(R.layout.setting_profile, container, false);
                init(rootView);
                GlobalApplication.getPresenter().loadUsers();
            }else
            {
                rootView=inflater.inflate(R.layout.fragment_settings,container,false);
                initSyncView(rootView);
            }
            return rootView;
        }

        @Override
        public void loadList(ArrayList<Users> list) {
            Users_adapter users_adapter=new Users_adapter(GlobalApplication.getAppContext(),list);
            usersSpiner.setAdapter(users_adapter);
        }

        @Override
        public void load(Object oneObject) {

        }

        @Override
        public Object getData() {
            return null;
        }


        public void init(View rootView)
        {
            GlobalApplication.getPresenter().setActivityModel(this);
            usersSpiner=rootView.findViewById(R.id.settings_usersSpiner);
            boolean isChecked=GlobalApplication.getPresenter().isSettingNotify();
            usersSpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    TextView user_id_view=view.findViewById(R.id.id_view);
                    int selected_user_id=Integer.parseInt(user_id_view.getText().toString());
                    GlobalApplication.getPresenter().setNotifyUser(selected_user_id);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            timePicker=rootView.findViewById(R.id.settings_notify_timepicker);
            timePicker.setIs24HourView(true);
            switchNotify=rootView.findViewById(R.id.settings_switchnotify);
            switchNotify.setChecked(isChecked);
            switchNotify.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    GlobalApplication.getPresenter().setSettingNotify(isChecked);

                    if ((isChecked)) {
                        timePicker.setVisibility(View.VISIBLE);
                        new Notify().start(false);// Стартует уведомление
                    }else{
                        timePicker.setVisibility(View.INVISIBLE);
                        new Notify().cencel(); //Отключает уведомление
                    }
                }
            });

            if(isChecked){
                timePicker.setVisibility(View.VISIBLE);
                 load_time();
            }else{
                timePicker.setVisibility(View.INVISIBLE);
            }
            timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
                @Override
                public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                    String time="1981-09-24 "+String.valueOf(hourOfDay)+":"+String.valueOf(minute)+":00";
                    GlobalApplication.getPresenter().setSettingsNotifyTime(time);

                }
            });
        }
        public void initSyncView(View syncView)
        {
            wifiSwitch=syncView.findViewById(R.id.settings_sync_wifi_switch);
            wifiSwitch.setChecked(GlobalApplication.getPresenter().isWyfiSync());
            wifiSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    GlobalApplication.getPresenter().setWifiSync(isChecked);
                }
            });
        }
        public void load_time()
        {
            Timestamp timestamp=GlobalApplication.getPresenter().getSettingsNotifyTime();
            timePicker.setHour(timestamp.getHours());
            timePicker.setMinute(timestamp.getMinutes());
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }

}
