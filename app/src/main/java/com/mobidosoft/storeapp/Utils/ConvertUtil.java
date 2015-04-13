package com.mobidosoft.storeapp.Utils;

/**
 * Created by RP on 3/18/2015.
 */
public class ConvertUtil {


    public static Integer stringToInteger(String num)
    {
        Integer r = null;
        if(num != null && !num.isEmpty())
        {
            r = Integer.parseInt(num);
        }
        return r;
    }

    public static Double stringToDouble(String num)
    {
        num = num.replace(',','.');
        Double r = null;
        if(num != null && !num.isEmpty())
        {
            r = Double.parseDouble(num);
        }
        return r;
    }

    public static Boolean stringToBoolean(String value)
    {
        Boolean r = null;
        if(value != null && !value.isEmpty())
        {
            if(value.equals("1"))
                return  true;

            if(value.equals("0"))
                return  false;

            r = Boolean.parseBoolean(value);
        }
        return r;
    }
}
