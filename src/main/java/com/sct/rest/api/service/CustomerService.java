package com.sct.rest.api.service;

import com.sct.rest.api.model.dto.CustomerDto;
import com.sct.rest.api.model.filter.CustomerPageableFilter;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * Получение отфильтрованной страницы с пользователями
     *
     * @param filter Параметры фильтрации и пагинации пользователей {@link CustomerPageableFilter}
     * @return Отфильтрованная страница с пользователями {@link CustomerDto}
     */
    Page<CustomerDto> getAllCustomerFilterAndPageable(CustomerPageableFilter filter);

    /**
     * Удаление текущего пользователя из системы
     */
    @Transactional
    void deleteCurrent();
}