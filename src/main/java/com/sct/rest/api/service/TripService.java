package com.sct.rest.api.service;

import com.sct.rest.api.model.dto.RentDto;
import com.sct.rest.api.model.dto.trip.TripBeginDto;
import com.sct.rest.api.model.dto.trip.TripEndDto;

/**
 * Сервис с бизнес-логикой связанной с арендами пользователей
 */
public interface TripService {

    /**
     * Начало поездки текущего пользователя в защищенной сессии
     *
     * @param tripBegin Параметры для начала поездки {@link TripBeginDto}
     * @return Созданная поездка {@link RentDto}
     */
    RentDto beginRent(TripBeginDto tripBegin);

    /**
     * Окончание поездки текущего пользователя в защищенной сессии
     *
     * @param tripEnd Параметры для окончания поездки {@link TripEndDto}
     * @return Оконченная поездка {@link RentDto}
     */
    RentDto endRent(TripEndDto tripEnd);
}