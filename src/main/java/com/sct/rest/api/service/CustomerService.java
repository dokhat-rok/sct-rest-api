package com.sct.rest.api.service;

import com.sct.rest.api.model.dto.CustomerDto;

/**
 * Сервис для работы с данными пользователей
 */
public interface CustomerService {

    /**
     * Получение пользователя по его идентификатору
     *
     * @param id Идентификатор пользователя
     * @return Объект типа {@link CustomerDto}
     */
    CustomerDto getUserById(Long id);

    /**
     * Получение текущего пользователя в защищенной сессии
     *
     * @return Объект типа {@link CustomerDto}
     */
    CustomerDto getCurrent();

    /**
     * Получение пользователя по его логину
     *
     * @param login Логин пользователя
     * @return Объект типа {@link CustomerDto}
     */
    CustomerDto getUserByLogin(String login);
}