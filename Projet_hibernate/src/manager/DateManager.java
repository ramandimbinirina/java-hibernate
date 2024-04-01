/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manager;

import bean.DateBean;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author BOENGY Miezaka
 */
public class DateManager {
    
    public List<DateBean> getDate(Date date){
        List<DateBean> dates = new ArrayList<>();
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        
        DateBean db = new DateBean();
        db.setDay(calendar.get(Calendar.DAY_OF_MONTH));
        db.setMonth(calendar.get(Calendar.MONTH));
        db.setYear(calendar.get(Calendar.YEAR));
        db.setHour(calendar.get(Calendar.HOUR_OF_DAY));
        db.setMinute(calendar.get(Calendar.MINUTE));
        
        dates.add(db);
        return dates;
        
    }
}
