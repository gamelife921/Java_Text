package WeatherForecast;

import java.time.LocalDate;

public class DateNow {


        String DateNow(){
            LocalDate date=LocalDate.now();

            int year=date.getYear();
            int month=date.getMonthValue();
            int day=date.getDayOfMonth();
            String strYear=year+"";
            String strMonth;
            String strDay;
            if(month<10)
                strMonth="0"+month;
            else
                strMonth=month+"";
            if(day<10)
                strDay="0"+day;
            else
            	strDay=day+"";

            String strDate=strYear.concat(strMonth);
            strDate=strDate.concat((strDay));

            return strDate;
        }

}
