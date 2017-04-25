package com.multshows.Views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.multshows.Adapter.AbstractWheelTextAdapter;
import com.multshows.Interfaces.OnWheelChangedListener;
import com.multshows.Interfaces.OnWheelScrollListener;
import com.multshows.R;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * 日期选择对话框  年月日 时  四列可选
 *
 * @author ywl
 */
public class ChangeClosingdateDialog extends Dialog implements View.OnClickListener {

    private Context context;
    private WheelView wvYear;//年
    private WheelView wvMonth;//月
    private WheelView wvDay;//日
    private WheelView wvhour;//时分

    private View vChangeClosingdate;
    private View vChangeClosingdateChild;
    private TextView btnSure;//确定按钮
    private TextView btnCancel;//返回按钮

    private ArrayList<String> arry_years = new ArrayList<String>();//存放年
    private ArrayList<String> arry_months = new ArrayList<String>();//存放月
    private ArrayList<String> arry_days = new ArrayList<String>();//存放日
    private ArrayList<String> arry_hours = new ArrayList<String>();//存放时
    private CalendarTextAdapter mYearAdapter;//年适配器
    private CalendarTextAdapter mMonthAdapter;//月适配器
    private CalendarTextAdapter mDaydapter;//日适配器
    private CalendarTextAdapter mHourdapter;//时适配器

    private int month;
    private int day;
    private int hour;

    private int currentYear = getYear();
    private int currentMonth = 1;
    private int currentDay = 1;

    private int maxTextSize = 24;
    private int minTextSize = 14;

    private boolean issetdata = false;

    private String selectYear;//选择的年
    private String selectMonth;//月
    private String selectDay;//日
    private String selecthour="12:00";

    private OnClosingdateListener onClosingdateListener;

    public ChangeClosingdateDialog(Context context) {
        super(context, R.style.ShareDialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_myinfo_changeclosingdate);
        wvYear = (WheelView) findViewById(R.id.wv_Closingdate_year);
        wvMonth = (WheelView) findViewById(R.id.wv_Closingdate_month);
        wvDay = (WheelView) findViewById(R.id.wv_Closingdate_day);
        wvhour = (WheelView) findViewById(R.id.wv_Closingdate_hour);

        vChangeClosingdate = findViewById(R.id.ly_myinfo_changeClosingdate);
        vChangeClosingdateChild = findViewById(R.id.ly_myinfo_changeClosingdate_child);
        btnSure = (TextView) findViewById(R.id.btn_myinfo_sure);
        btnCancel = (TextView) findViewById(R.id.btn_myinfo_cancel);

        vChangeClosingdate.setOnClickListener(this);
        vChangeClosingdateChild.setOnClickListener(this);
        btnSure.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        if (!issetdata) {
            initData();
        }
        initYears();
        mYearAdapter = new CalendarTextAdapter(context, arry_years, setYear(currentYear), maxTextSize, minTextSize);
        wvYear.setVisibleItems(5);
        wvYear.setViewAdapter(mYearAdapter);
        wvYear.setCurrentItem(setYear(currentYear));

        initMonths(month);
        mMonthAdapter = new CalendarTextAdapter(context, arry_months, setMonth(currentMonth), maxTextSize, minTextSize);
        wvMonth.setVisibleItems(5);
        wvMonth.setViewAdapter(mMonthAdapter);
        wvMonth.setCurrentItem(setMonth(currentMonth));

        initDays(day);
        mDaydapter = new CalendarTextAdapter(context, arry_days, currentDay - 1, maxTextSize, minTextSize);
        wvDay.setVisibleItems(5);
        wvDay.setViewAdapter(mDaydapter);
        wvDay.setCurrentItem(currentDay - 1);

        initHour(24);
        Log.e("size",arry_hours.size()+"");
        mHourdapter = new CalendarTextAdapter(context, arry_hours, 23, maxTextSize, minTextSize);
        wvhour.setVisibleItems(5);
        wvhour.setViewAdapter(mHourdapter);
        wvhour.setCurrentItem(11);


        wvYear.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                // TODO Auto-generated method stub
                String currentText = (String) mYearAdapter.getItemText(wheel.getCurrentItem());
                selectYear = currentText;
                setTextviewSize(currentText, mYearAdapter);
                currentYear = Integer.parseInt(currentText);
                setYear(currentYear);
                initMonths(month);
                mMonthAdapter = new CalendarTextAdapter(context, arry_months, 24, maxTextSize, minTextSize);
                wvMonth.setVisibleItems(5);
                wvMonth.setViewAdapter(mMonthAdapter);
                wvMonth.setCurrentItem(0);
            }
        });

        wvYear.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                // TODO Auto-generated method stub
                String currentText = (String) mYearAdapter.getItemText(wheel.getCurrentItem());
                setTextviewSize(currentText, mYearAdapter);
            }
        });
        wvMonth.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                // TODO Auto-generated method stub
                String currentText = (String) mMonthAdapter.getItemText(wheel.getCurrentItem());
                selectMonth = currentText;
                setTextviewSize(currentText, mMonthAdapter);
                setMonth(Integer.parseInt(currentText));
                Log.e("Integer.parseInt", Integer.parseInt(selectYear) + ":" + Integer.parseInt(selectMonth));
                initDays(getCalDays(Integer.parseInt(selectYear), Integer.parseInt(selectMonth)));
                mDaydapter = new CalendarTextAdapter(context, arry_days, 0, maxTextSize, minTextSize);
                wvDay.setVisibleItems(5);
                wvDay.setViewAdapter(mDaydapter);
                wvDay.setCurrentItem(0);
            }
        });

        wvMonth.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                // TODO Auto-generated method stub
                String currentText = (String) mMonthAdapter.getItemText(wheel.getCurrentItem());
                setTextviewSize(currentText, mMonthAdapter);
            }
        });

        wvDay.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                // TODO Auto-generated method stub
                String currentText = (String) mDaydapter.getItemText(wheel.getCurrentItem());
                setTextviewSize(currentText, mDaydapter);
                selectDay = currentText;
            }
        });

        wvDay.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                // TODO Auto-generated method stub
                String currentText = (String) mDaydapter.getItemText(wheel.getCurrentItem());
                setTextviewSize(currentText, mDaydapter);
            }
        });
        wvhour.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                // TODO Auto-generated method stub
                String currentText = (String) mHourdapter.getItemText(wheel.getCurrentItem());
                setTextviewSize(currentText, mHourdapter);
                selecthour = currentText;
            }
        });

        wvhour.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                // TODO Auto-generated method stub
                String currentText = (String) mHourdapter.getItemText(wheel.getCurrentItem());
                setTextviewSize(currentText, mHourdapter);
            }
        });

    }

    //设置年
    public void initYears() {
        for (int i = getYear(); i < 2060; i++) {
            arry_years.add(i + "");
        }
    }

    public void initMonths(int months) {
        arry_months.clear();
        for (int i = 1; i <= months; i++) {
            arry_months.add(i + "");
        }
    }

    public void initDays(int days) {
        arry_days.clear();
        for (int i = 1; i <= days; i++) {
            arry_days.add(i + "");
        }
    }

    public void initHour(int hour) {
        arry_hours.clear();
        for (int i = 1; i <= hour; i++) {
            if (i < 10)
                arry_hours.add("0" + i + ":00");
            if (i >= 10)
                arry_hours.add(i + ":00");
        }
    }

    private class CalendarTextAdapter extends AbstractWheelTextAdapter {
        ArrayList<String> list;

        protected CalendarTextAdapter(Context context, ArrayList<String> list, int currentItem, int maxsize, int minsize) {
            super(context, R.layout.item_closingdate_year, NO_RESOURCE, currentItem, maxsize, minsize);
            this.list = list;
            setItemTextResource(R.id.tempValue);
        }

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            View view = super.getItem(index, cachedView, parent);
            return view;
        }

        @Override
        public int getItemsCount() {
            return list.size();
        }

        @Override
        protected CharSequence getItemText(int index) {
            return list.get(index) + "";
        }
    }

    public void setClosingdateListener(OnClosingdateListener onClosingdateListener) {
        this.onClosingdateListener = onClosingdateListener;
    }

    @Override
    public void onClick(View v) {

        if (v == btnSure) {
            if (onClosingdateListener != null) {
                onClosingdateListener.onClick(selectYear, selectMonth, selectDay,selecthour);
            }
        } else if (v == btnSure) {

        } else if (v == vChangeClosingdateChild) {
            return;
        } else {
            dismiss();
        }
        dismiss();

    }

    public interface OnClosingdateListener {
        void onClick(String year, String month, String day, String hour);
    }

    /**
     * 设置字体大小
     *
     * @param curriteItemText
     * @param adapter
     */
    public void setTextviewSize(String curriteItemText, CalendarTextAdapter adapter) {
        ArrayList<View> arrayList = adapter.getTestViews();
        int size = arrayList.size();
        String currentText;
        for (int i = 0; i < size; i++) {
            TextView textvew = (TextView) arrayList.get(i);
            currentText = textvew.getText().toString();
            if (curriteItemText.equals(currentText)) {
                textvew.setTextSize(maxTextSize);
            } else {
                textvew.setTextSize(minTextSize);
            }
        }
    }

    public int getYear() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.YEAR);
    }

    public int getMonth() {
        Calendar c = Calendar.getInstance();
        return 12;
    }

    public int getDay() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.DATE);
    }

    public void initData() {
        setDate(getYear(), getMonth(), getDay());
        this.currentDay = 1;
        this.currentMonth = 1;
    }

    /**
     * 设置年月日
     *
     * @param year
     * @param month
     * @param day
     */
    public void setDate(int year, int month, int day) {
        selectYear = year + "";
        selectMonth = month + "";
        selectDay = day + "";
        issetdata = true;
        this.currentYear = year;
        this.currentMonth = month;
        this.currentDay = day;
        if (year == getYear()) {
            this.month = getMonth();
        } else {
            this.month = 12;
        }
        calDays(year, month);
    }

    /**
     * 设置年份
     *
     * @param year
     */
    public int setYear(int year) {
        int yearIndex = 0;
        if (year != getYear()) {
            this.month = 12;
        } else {
            this.month = getMonth();
        }
        for (int i = getYear(); i < 2060; i++) {
            if (i == year) {
                return yearIndex;
            }
            yearIndex++;
        }
        return yearIndex;
    }

    /**
     * 设置月份
     *
     * @param year
     * @param month
     * @return
     */
    public int setMonth(int month) {
        int monthIndex = 0;
        calDays(currentYear, 12);
        for (int i = 1; i <= 12; i++) {
            if (month == i) {
                return monthIndex;
            } else {
                monthIndex++;
            }
        }
        return monthIndex;
    }

    /**
     * 计算每月多少天
     *
     * @param month
     * @param leayyear
     */
    public void calDays(int year, int month) {
        boolean leayyear = false;
        leayyear = year % 4 == 0 && year % 100 != 0;
        for (int i = 1; i <= 12; i++) {
            switch (month) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    this.day = 31;
                    break;
                case 2:
                    if (leayyear) {
                        this.day = 29;
                    } else {
                        this.day = 28;
                    }
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    this.day = 30;
                    break;
            }
        }
        /*if (year == getYear() && month == getMonth()) {
			this.day = getDay();
		}*/
    }

    /**
     * 计算每月多少天
     *
     * @param month
     * @param leayyear
     */
    public int getCalDays(int year, int month) {
        int returnDay = 30;
        boolean leayyear = false;
        leayyear = year % 4 == 0 && year % 100 != 0;

        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                returnDay = 31;
                break;
            case 2:
                if (leayyear) {
                    returnDay = 29;
                } else {
                    returnDay = 28;
                }
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                returnDay = 30;
                break;
        }
        return returnDay;
    }
}