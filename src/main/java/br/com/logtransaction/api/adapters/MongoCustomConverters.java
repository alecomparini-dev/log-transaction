package br.com.logtransaction.api.adapters;

import com.mongodb.lang.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Configuration
public class MongoCustomConverters {

    @Bean
    public MongoCustomConversions mongoCustomConversions() {
        List<Converter<?,?>> converters = new ArrayList<>();
        converters.add(ZonedDateTimeToDate.INSTANCE);
        converters.add(DateToZonedDateTime.INSTANCE);
        return new MongoCustomConversions(converters);
    }

    @ReadingConverter
    enum DateToZonedDateTime implements Converter<LocalDateTime, ZonedDateTime> {

        INSTANCE;

        @Override
        public ZonedDateTime convert(LocalDateTime date) {
            return date.atZone(ZoneId.systemDefault());

        }
    }

    @WritingConverter
    enum ZonedDateTimeToDate implements Converter<ZonedDateTime, Date> {

        INSTANCE;

        @Override
        public Date convert(ZonedDateTime zonedDateTime) {
            return Date.from(zonedDateTime.toInstant());
        }
    }
}
