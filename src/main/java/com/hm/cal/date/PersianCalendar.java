/*****************************************************************************
 * Copyright 2016 Hypotemoose, Inc.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *****************************************************************************/
package com.hm.cal.date;

import com.hm.cal.util.AlmanacConverter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.joda.time.DateTime;

import static com.hm.cal.constants.CalendarConstants.PersianCalendarConstants.monthNames;
import static com.hm.cal.constants.CalendarConstants.PersianCalendarConstants.weekDayNames;
import static com.hm.cal.util.AlmanacConverter.toPersianCalendar;

/**
 * A Persian (Jalali) calendar date.
 * <p>
 * The Persian calendar was officially adopted in 1925. Each year consists of
 * 12 months. The beginning of the calendar is determined by the day on which
 * the March equinox occurs at or after noon at with reference to Iran
 * standard time. Because of this, leap years have no set rule, however an
 * additional day can be added to the last month on years where there is
 * discrepancy.
 *
 * @author Chris Engelsma, Hypotemoose
 * @since 2016.05.17
 */
public class PersianCalendar extends Almanac {

  public static final String CALENDAR_NAME = "Persian Calendar";
  public static final JulianDay EPOCH = new JulianDay(1948320.5);

  /**
   * Constructs a new Persian Calendar using today's date.
   */
  public PersianCalendar() {
    this(new JulianDay());
  }

  /**
   * Constructs a new Persian Calendar using an existing Almanac.
   *
   * @param a an Almanac.
   */
  public PersianCalendar(Almanac a) {
    this(toPersianCalendar(a));
  }

  public PersianCalendar(DateTime dateTime) {
    this(new GregorianCalendar(dateTime));
  }

  /**
   * Constructs a new Persian Calendar using an existing Persian Calendar.
   *
   * @param date an existing Persian calendar.
   */
  public PersianCalendar(PersianCalendar date) {
    this(date.getYear(), date.getMonth(), date.getDay());
  }

  /**
   * Constructs a new Persian Calendar using input year, month and day.
   *
   * @param year  a year.
   * @param month a month.
   * @param day   a day.
   */
  public PersianCalendar(int year, int month, int day) {
    super();
    this.day = day;
    this.month = month;
    this.year = year;
  }

  /**
   * Determines whether a given year is a leap year.
   *
   * @param year a year.
   * @return true, if is a leap year; false, otherwise.
   */
  public static boolean isLeapYear(int year) {
    PersianCalendar cal = new PersianCalendar(year, 1, 1);
    PersianCalendar calp1 = new PersianCalendar(year + 1, 1, 1);
    JulianDay jd = AlmanacConverter.toJulianDay(cal);
    JulianDay jdp1 = AlmanacConverter.toJulianDay(calp1);
    return (jdp1.getValue() - jd.getValue() > 365);
  }

  /**
   * Gets the number of days per month in a given year.
   *
   * @param year a year.
   * @return an array[12] containing the month lengths.
   */
  public static int[] getDaysPerMonthInYear(int year) {
    int[] days = new int[12];
    for (int i = 0; i < 11; ++i) {
      days[i] = (i <= 5) ? 31 : 30;
    }
    days[11] = (isLeapYear(year) ? 30 : 29);
    return days;
  }

  /**
   * Gets the number of days in a given month in a given year.
   *
   * @param year  a year.
   * @param month a month [1-12].
   * @return the number of days in the given month/year;
   */
  public static int getNumberOfDaysInMonth(int year, int month) {
    if (month <= 6) return 31;
    else {
      if (month != 12) return 30;
      return (isLeapYear(year) ? 30 : 29);
    }
  }

  /**
   * Gets a month name.
   *
   * @param month the month number [1-12].
   * @return the name of the month.
   * @throws IndexOutOfBoundsException
   */
  public static String getMonthName(int month)
    throws IndexOutOfBoundsException {
    return monthNames[month - 1];
  }

  @Override
  public String getDate() {
    return new String(getDay() + " " +
      getMonthName() + ", " +
      getYear());
  }

  /**
   * Gets the month names.
   *
   * @return an array[12] containing the month names.
   */
  public String[] getMonths() {
    return monthNames;
  }

  /**
   * Gets the week day names.
   *
   * @return an array[7] containing the week day names;
   */
  public String[] getWeekDays() {
    return weekDayNames;
  }

  /**
   * Gets the name for this week day.
   *
   * @return the name for this week day.
   */
  @Override
  public String getWeekDay() {
    return weekDayNames[getWeekDayNumber()];
  }

  /**
   * Determines whether this date's year is a leap year.
   *
   * @return true, if this is a leap year; false, otherwise.
   */
  public boolean isLeapYear() {
    return PersianCalendar.isLeapYear(this.year);
  }

  /**
   * Gets this month's name.
   *
   * @return the name of this month.
   */
  public String getMonthName() {
    return PersianCalendar.getMonthName(this.month);
  }

  /**
   * Gets the name of this calendar.
   *
   * @return the name of this calendar.
   */
  @Override
  public String getName() {
    return CALENDAR_NAME;
  }

  /**
   * Gets the number of days in this month.
   *
   * @return the number of days in this month.
   */
  @Override
  public int getNumberOfDaysInMonth() {
    return PersianCalendar.getNumberOfDaysInMonth(this.year, this.month);
  }

  /**
   * Gets the number of days in a week.
   *
   * @return the number of days in a week.
   */
  @Override
  public int getNumberOfDaysInWeek() {
    return 7;
  }

  /**
   * Gets the number of months in a year.
   *
   * @return the number of months in a year.
   */
  @Override
  public int getNumberOfMonthsInYear() {
    return 12;
  }

  /**
   * Gets the days per month in this year.
   *
   * @return an array[12] of month lengths.
   */
  public int[] getDaysPerMonthInYear() {
    return PersianCalendar.getDaysPerMonthInYear(getYear());
  }

  /**
   * Sets this calendar using another Almanac.
   *
   * @param a an Almanac.
   */
  @Override
  public void set(Almanac a) {
    PersianCalendar cal = toPersianCalendar(a);
    this.year = cal.getYear();
    this.month = cal.getMonth();
    this.day = cal.getDay();
  }

  @Override
  public String toString() {
    return new String(CALENDAR_NAME + ": " + getDate());
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof PersianCalendar))
      return false;
    if (obj == this)
      return true;

    final PersianCalendar date = (PersianCalendar) obj;
    return new EqualsBuilder()
      .append(this.day, date.getDay())
      .append(this.month, date.getMonth())
      .append(this.year, date.getYear())
      .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder()
      .append(this.day)
      .append(this.month)
      .append(this.year)
      .toHashCode();
  }

}
