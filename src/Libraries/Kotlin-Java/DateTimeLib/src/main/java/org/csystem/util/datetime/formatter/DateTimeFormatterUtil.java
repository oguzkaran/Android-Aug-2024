package org.csystem.util.datetime.formatter;

    import java.time.format.DateTimeFormatter;

    /**
     * Utility class for datetime formatting operations.
     */
    public final class DateTimeFormatterUtil {
        private DateTimeFormatterUtil()
        {}

        /**
         * Formatter for date in the format "dd/MM/yyyy".
         */
        public static DateTimeFormatter DATE_TR = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        /**
         * Formatter for date in the format "dd.MM.yyyy".
         */
        public static DateTimeFormatter DATE_DOT_TR = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        /**
         * Formatter for date in the format "dd-MM-yyyy".
         */
        public static DateTimeFormatter DATE_HYPHEN_TR = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        /**
         * Formatter for datetime with seconds in the format "dd/MM/yyyy HH:mm:ss".
         */
        public static DateTimeFormatter DATETIME_SEC_TR = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        /**
         * Formatter for datetime with seconds and AM/PM in the format "dd/MM/yyyy hh:mm:ss a".
         */
        public static DateTimeFormatter DATETIME_SEC_A_TR = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss a");

        /**
         * Formatter for datetime with seconds in the format "dd.MM.yyyy HH:mm:ss".
         */
        public static DateTimeFormatter DATETIME_DOT_SEC_TR = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

        /**
         * Formatter for datetime with seconds in the format "dd-MM-yyyy HH:mm:ss".
         */
        public static DateTimeFormatter DATETIME_HYPHEN_SEC_TR = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        /**
         * Formatter for datetime in the format "dd/MM/yyyy HH:mm".
         */
        public static DateTimeFormatter DATETIME_TR = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        /**
         * Formatter for datetime in the format "dd.MM.yyyy HH:mm".
         */
        public static DateTimeFormatter DATETIME_DOT_TR = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

        /**
         * Formatter for datetime in the format "dd-MM-yyyy HH:mm".
         */
        public static DateTimeFormatter DATETIME_HYPHEN_TR = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        /**
         * Formatter for time in the format "HH:mm".
         */
        public static DateTimeFormatter TIME = DateTimeFormatter.ofPattern("HH:mm");

        /**
         * Formatter for time with seconds in the format "HH:mm:ss".
         */
        public static DateTimeFormatter TIME_SEC = DateTimeFormatter.ofPattern("HH:mm:ss");
        //...
    }