import java.util.Calendar;

/**
 * Created by Tanner on 9/20/2016.
 */
class MyDate {
    private int year;
    private int month;
    private String monthString;
    private int day;
    private int hour;
    private int minute;
    private int second;
    private int millisecond;

    MyDate(Calendar c) {
        year = c.get(Calendar.YEAR);
        this.month = c.get(Calendar.MONTH) + 1;
        this.monthString = convertMonthString(this.month);
        this.day = c.get(Calendar.DAY_OF_MONTH);
        this.hour = c.get(Calendar.HOUR_OF_DAY);
        this.minute = c.get(Calendar.MINUTE);
        this.second = c.get(Calendar.SECOND);
        this.millisecond = c.get(Calendar.MILLISECOND);
    }

    int getYear() {
        return year;
    }

    int getMonth() {
        return month;
    }

    public String getMonthString(){
        return monthString;
    }

    int getDay() {
        return day;
    }

    int getHour() {
        return hour;
    }

    int getMinute() {
        return minute;
    }

    private String convertMonthString(int month){
        switch(month){
            case 1:
                return "January";
            case 2:
                return "February";
            case 3:
                return "March";
            case 4:
                return "April";
            case 5:
                return "May";
            case 6:
                return "June";
            case 7:
                return "July";
            case 8:
                return "August";
            case 9:
                return "September";
            case 10:
                return "October";
            case 11:
                return "November";
            case 12:
                return "December";
            default:
                return "";
        }
    }
}
