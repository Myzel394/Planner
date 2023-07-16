package app.myzel394.planner.utils

import kotlinx.datetime.LocalTime
import kotlinx.datetime.atDate
import kotlinx.datetime.LocalDate;
import kotlinx.datetime.LocalDateTime;
import kotlinx.datetime.atTime
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toJavaLocalDateTime
import java.text.DateFormat
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Date

fun formatTime(
    time: LocalTime,
): String {
    val datetime = time.atDate(LocalDate(2000, 1, 1)).toJavaLocalDateTime()

    return DateTimeFormatter
        .ofPattern("HH:mm")
        .format(datetime);
}

fun formatDate(
    date: LocalDate,
    dateFormat: Int = DateFormat.LONG,
): String {
    val pureDate = Date
        .from(
            date
                .atTime(LocalTime(1, 1))
                .toJavaLocalDateTime()
                .toInstant(ZoneOffset.UTC)
        );

    return DateFormat.getDateInstance(dateFormat).format(pureDate);
}

fun toISOString(
    datetime: LocalDateTime,
): String {
    val offsetDate = OffsetDateTime.of(datetime.toJavaLocalDateTime(), ZoneOffset.UTC);

    return DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(offsetDate).substring(0, 19);
}

fun toISOString(
    date: LocalDate,
): String {
    return toISOString(date.atTime(1, 1));
}
