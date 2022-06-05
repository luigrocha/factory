package org.crsoft.cartonplast.common.util;

import org.crsoft.cartonplast.common.constant.GlobalConstant;
import org.crsoft.cartonplast.common.constant.ReceiptConstant;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

/**
 * @author lpillaga on 04/06/2022
 */
public class DateUtil {

    private DateUtil() {
    }

    public static String generateReceiptDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "";
        }

        DateTimeFormatter format = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendPattern(ReceiptConstant.RECEIPT_DATE_TIME_FORMAT)
                .toFormatter(new Locale(GlobalConstant.ES_LANG_CODE, GlobalConstant.EC_CODE));

        return dateTime.format(format);
    }
}
