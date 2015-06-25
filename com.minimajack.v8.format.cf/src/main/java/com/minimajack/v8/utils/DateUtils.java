package com.minimajack.v8.utils;

import java.util.Date;

public class DateUtils
{
    private static final long MILLISEC_TO_UNIXTIME_X10 = 621356075920000L;

    public static long tov8Time( Date date )
    {
        return MILLISEC_TO_UNIXTIME_X10 + date.getTime() * 10;
    }

    public static Date fromv8Time( Long v8date )
    {
        return new Date( ( v8date - MILLISEC_TO_UNIXTIME_X10 ) / 10 );
    }
}
