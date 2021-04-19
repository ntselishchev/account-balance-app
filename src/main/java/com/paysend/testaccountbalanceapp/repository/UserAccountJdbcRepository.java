package com.paysend.testaccountbalanceapp.repository;

import com.paysend.testaccountbalanceapp.model.domain.UserAccount;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class UserAccountJdbcRepository {
    private final NamedParameterJdbcOperations jdbcOperations;

    public void save(UserAccount account) {
        String sql = "INSERT INTO accounts (user_id) VALUES (:userId)";
        jdbcOperations.update(
                sql,
                new MapSqlParameterSource()
                        .addValue("userId", account.getUserId())
        );
    }
}
