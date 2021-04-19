package com.paysend.testaccountbalanceapp.repository;

import com.paysend.testaccountbalanceapp.model.domain.PaysendUserDetails;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static java.util.Optional.ofNullable;

@Repository
@AllArgsConstructor
public class UserJdbcRepository {
    private final NamedParameterJdbcOperations jdbcOperations;

    public Optional<PaysendUserDetails> findByUsername(String username) {
        String sql = "SELECT username    AS username, " +
                "            password    AS password " +
                "FROM users " +
                "WHERE username = :username";
        try {
            PaysendUserDetails userDetails = jdbcOperations.queryForObject(
                    sql,
                    new MapSqlParameterSource().addValue("username", username),
                    new BeanPropertyRowMapper<>(PaysendUserDetails.class)
            );
            return ofNullable(userDetails);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public PaysendUserDetails save(PaysendUserDetails user) {
        String sql = "INSERT INTO users (username, password) " +
                "VALUES (:username, :password) " +
                "RETURNING *";
        return jdbcOperations.queryForObject(
                sql,
                new MapSqlParameterSource()
                        .addValue("username", user.getUsername())
                        .addValue("password", user.getPassword()),
                new BeanPropertyRowMapper<>(PaysendUserDetails.class)
        );
    }

    public Double getBalance(String username) {
        String sql = "SELECT COALESCE(SUM(tr.amount), 0) AS AMOUNT " +
                "FROM transactions tr " +
                "JOIN accounts ac ON ac.id = tr.account_id " +
                "JOIN users us ON us.id = ac.user_id " +
                "WHERE us.username = :username";
        Long amount = jdbcOperations.queryForObject(sql,
                new MapSqlParameterSource()
                        .addValue("username", username),
                Long.class
        );
        return ofNullable(amount).orElse(0L)
                .doubleValue() / 100;
    }
}
