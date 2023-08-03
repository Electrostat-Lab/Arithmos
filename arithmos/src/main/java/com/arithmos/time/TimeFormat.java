package com.arithmos.time;

/**
 * TimeFormat Utility Class.
 * 
 * @challenge by 2Math0.
 * @author pavl_g
 */
public final class TimeFormat {
    private static Time.InputType inputType = Time.InputType.INPUT_TYPE_SECS;
    
    private TimeFormat() {
    
    }
    
    /**
     * Uses & converts Time from millis to a time unit.
     */
    public static class Time {
        //applying the maths substituting methods on java_enums
        public enum InputType{
            INPUT_TYPE_NANOS(1d),
            INPUT_TYPE_MICROS(INPUT_TYPE_NANOS.unit * 1000d),
            INPUT_TYPE_MILLIS(INPUT_TYPE_MICROS.unit * 1000d),
            INPUT_TYPE_SECS(INPUT_TYPE_MILLIS.unit * 1000d);
            public final double unit;
            InputType(final double unit){
                this.unit = unit;
            }
        }
        public enum Unit {
            //conversion pattern from nanos up-to years
            NANOS(inputType.unit),
            MICROS(NANOS.unit / 1000d),
            MILLIS(MICROS.unit / 1000d),
            SECONDS(MILLIS.unit / 1000d),
            MINUTES(SECONDS.unit / 60d),
            HOURS(MINUTES.unit / 60d),
            DAYS(HOURS.unit / 24d),
            WEEKS(DAYS.unit / 7d),
            MONTHS(WEEKS.unit / 4d),
            YEARS(MONTHS.unit / 12d);

            public final double unit;

            /**
             * Converts Millis to a specific TimeUnit
             * @param unit the conversion formula.
             */
            Unit(final double unit){
                this.unit = unit;
            }
        }
    }

    /**
     * Wraps an output format for the specific time input.
     */
    public static final class TimeRepresentFormat {
        public static Format format = Format.HRS_MINS_SECS;

        /**
         * The formats wrapper
         */
        public enum Format{
            COLON_NO_LABELS(1000),
            HRS_MINS_SECS(1231),
            HRS_COLON_MINS_COLON_SECS(4141);
            public final int formatId;
            Format(final int formatId){
                this.formatId = formatId;
            }
        }

        /**
         * Assigns an output format from the user end.
         * @param format the format, select from #{@link Format#HRS_COLON_MINS_COLON_SECS}, #{@link Format#HRS_MINS_SECS}.
         */
        public static void assignFormat(Format format) {
            TimeRepresentFormat.format = format;
        }

        /**
         * Gets the time in the assigned format,
         * @param seconds seconds to output.
         * @param minutes minutes to output.
         * @param hours hours to output.
         * @param days days to output.
         * @param weeks weeks to output.
         * @param months months to output.
         * @param years years to output
         * @return a formatted time.
         */
        public static String getTime(double seconds, double minutes, double hours,
                                     double days, double weeks, double months, double years){
                String s_seconds, s_minutes, s_hours, s_days, s_weeks, s_months, s_years;
                if (format.formatId == Format.COLON_NO_LABELS.formatId) {
                    s_seconds = getPostfix(seconds);
                    s_minutes = getPostfix(minutes);
                    s_hours = getPostfix(hours + days/(24 * 7) + weeks/(24 * 4) + months/(24 * 12));
                    return s_hours + ":" + s_minutes + ":" + s_seconds; 
                } else if (format.formatId == Format.HRS_COLON_MINS_COLON_SECS.formatId) {
                    //do the other formats
                    s_seconds = ((seconds < 1) ? "" : (getPostfix(seconds) + " SECS"));
                    s_minutes = ((minutes < 1) ? "" : (getPostfix(minutes) + " MINS : "));
                    s_hours = ((hours < 1) ? "" : (getPostfix(hours) + " HRS : "));
                    s_days = ((days < 1) ? "" : (getPostfix(days) + " DYS : "));
                    s_weeks = ((weeks < 1) ? "" : (getPostfix(weeks) + " WKS : "));
                    s_months = ((months < 1) ? "" : (getPostfix(months) + " MNTHS : "));
                    s_years = ((years < 1) ? "" : (getPostfix(years) + " YRS : "));
                    if(s_seconds.contains("SECS")){
                        s_seconds += ".";
                    }else if(s_minutes.contains("MINS")){
                        s_minutes = s_minutes.replaceAll(": ", ".");
                    }else if(s_hours.contains("HRS")){
                        s_hours = s_hours.replaceAll(": ", ".");
                    }else if(s_days.contains("DYS")){
                        s_days = s_days.replaceAll(": ", ".");
                    }else if(s_weeks.contains("WKS")) {
                        s_weeks = s_weeks.replaceAll(": ", ".");
                    }else if(s_months.contains("MNTHS")){
                        s_months = s_months.replaceAll(": ", ".");
                    }else if(s_years.contains("YRS")){
                        s_years = s_years.replaceAll(": ", ".");
                    }
                    return s_years + s_months + s_weeks + s_days + s_hours + s_minutes + s_seconds;
                }
                //------------------default operation-----------------
                //return a specific format
                s_seconds = ((seconds < 1) ? "" : Math.round(seconds) + getPostfix(" second", seconds));
                s_minutes = ((minutes < 1) ? "" : Math.round(minutes) + getPostfix(" minute", minutes) + ", ");
                s_hours = ((hours < 1) ? "" : Math.round(hours) + getPostfix(" hour", hours) + ", ");
                s_days = ((days < 1) ? "" : Math.round(days) + getPostfix(" day", days) + ", ");
                s_weeks = ((weeks < 1) ? "" : Math.round(weeks) + getPostfix(" week", weeks) + ", ");
                s_months = ((months < 1) ? "" : Math.round(months) + getPostfix(" month", months) + ", ");
                s_years = ((years < 1) ? "" : Math.round(years) + getPostfix(" year", years) + ", ");
                if(s_seconds.contains("second")){
                    s_seconds += ".";
                }else if(s_minutes.contains("minute")){
                    s_minutes = s_minutes.replaceAll(", ", ".");
                }else if(s_hours.contains("hour")){
                    s_hours = s_hours.replaceAll(", ", ".");
                }else if(s_days.contains("day")){
                    s_days = s_days.replaceAll(", ", ".");
                }else if(s_weeks.contains("week")) {
                    s_weeks = s_weeks.replaceAll(", ", ".");
                }else if(s_months.contains("month")){
                    s_months = s_months.replaceAll(", ", ".");
                }else if(s_years.contains("year")){
                    s_years = s_years.replaceAll(", ", ".");
                }

            return s_years + s_months + s_weeks + s_days
                    + s_hours + s_minutes + s_seconds;
        }
        private static String getPostfix(final String acronym, final double value){
                if(Math.round(value) > 1d){
                    return acronym + "s";
                }else if(value == 0){
                    return "";
                }
            return acronym;
        }
        private static String getPostfix(final double value){
            if(Math.round(value) < 10d){
                return "0" + Math.round(value);
            }else if(Math.round(value) == 0){
                return "";
            }
            return "" + Math.round(value);
        }
    }

    /**
     * Gets an input time in a specific format.
     * @param format the output format,select from
     *               #{@link TimeFormat.TimeRepresentFormat.Format#HRS_COLON_MINS_COLON_SECS}, #{@link TimeFormat.TimeRepresentFormat.Format#HRS_MINS_SECS}.
     * @param inputTime time in the specified format.
     * @param inputType the type of time input.
     * @return the formatted time.
     */
    public static String getFormattedTime(final TimeRepresentFormat.Format format, final double inputTime, final Time.InputType inputType){
            TimeFormat.inputType = inputType;
            //convert time
            double seconds, minutes, hours, days, weeks, months, years;
            //convert millis to seconds & limit the seconds by 60 seconds
            seconds = (inputTime * Time.Unit.SECONDS.unit) % 60;
            //convert millis to minutes & limit the minutes by 60 minutes
            minutes = (inputTime * Time.Unit.MINUTES.unit) % 60;
            //convert millis to hours & limit the hours by 24 hours
            hours = (inputTime * Time.Unit.HOURS.unit) % 24;
            //convert millis to days & limit days to 7 days
            days = (inputTime * Time.Unit.DAYS.unit) % 7;
            //convert millis to weeks & limit weeks to 4 weeks
            weeks = (inputTime * Time.Unit.WEEKS.unit) % 4;
            //convert millis to months & limit months to 12 months
            months = (inputTime * Time.Unit.MONTHS.unit) % 12;
            //convert millis to years with no limit
            years = inputTime * Time.Unit.YEARS.unit;

            //assign output format
            TimeRepresentFormat.assignFormat(format);
        return TimeRepresentFormat.getTime(seconds, minutes, hours, days, weeks, months, years);
    }


}
