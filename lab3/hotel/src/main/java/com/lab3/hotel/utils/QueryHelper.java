package com.lab3.hotel.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class QueryHelper {
    private static final String INITIAL_ERROR_MESSAGE = "Error occurred during ";
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public <T> Optional<T> queryForObject(String sql, MapSqlParameterSource params, Class<T> tCls, String errorMessage) {
        try {
            T result = namedParameterJdbcTemplate.queryForObject(sql, params, tCls);
            return Optional.ofNullable(result);
        } catch (EmptyResultDataAccessException e) {
          return Optional.empty();
        } catch (RuntimeException e) {
            log.error(errorMessage, e);
            throw new RuntimeException(INITIAL_ERROR_MESSAGE + errorMessage);
        }
    }

    public <T> Optional<T> queryForObject(String sql, MapSqlParameterSource params, RowMapper<T> mapper, String errorMessage) {
        try {
            T result = namedParameterJdbcTemplate.queryForObject(sql, params, mapper);
            return Optional.ofNullable(result);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        } catch (RuntimeException e) {
            log.error(errorMessage, e);
            throw new RuntimeException(INITIAL_ERROR_MESSAGE + errorMessage);
        }
    }

    public <T> T queryForObjectWithoutEmptyCheck(String sql, MapSqlParameterSource params, Class<T> tCls, String errorMessage) {
        try {
            return namedParameterJdbcTemplate.queryForObject(sql, params, tCls);
        } catch (RuntimeException e) {
            log.error(errorMessage, e);
            throw new RuntimeException(INITIAL_ERROR_MESSAGE + errorMessage);
        }
    }

    public <T> List<T> query(String sql, MapSqlParameterSource params, RowMapper<T> mapper, String errorMessage) {
        try {
            return namedParameterJdbcTemplate.query(sql, params, mapper);
        } catch (RuntimeException e) {
            log.error(errorMessage, e);
            throw new RuntimeException(INITIAL_ERROR_MESSAGE + errorMessage);
        }
    }

    public void update(String sql, MapSqlParameterSource params, String errorMessage) {
        try {
             namedParameterJdbcTemplate.update(sql, params);
        } catch (RuntimeException e) {
            log.error(errorMessage, e);
            throw new RuntimeException(INITIAL_ERROR_MESSAGE + errorMessage);
        }
    }


    public void batchUpdate(String sql, BatchPreparedStatementSetter preparedStatementSetter, String errorMessage) {
        try {
            jdbcTemplate.batchUpdate(sql, preparedStatementSetter);
        } catch (RuntimeException e) {
            log.error(errorMessage, e);
            throw new RuntimeException(INITIAL_ERROR_MESSAGE + errorMessage);
        }
    }
}
