package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class JdbcAccountDao implements AccountDao {

    private JdbcTemplate jdbcTemplate;
    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public boolean createAccount(Account account) {
        String accountSql = "INSERT INTO account(user_id, balance) VALUES (?, ?)";
        BigDecimal initialBalance = new BigDecimal("1000.00");
        account.setBalance(initialBalance);
        Integer newAccountId = 2001;
        try {
            newAccountId = jdbcTemplate.queryForObject(accountSql, Integer.class, account);
        } catch (DataAccessException e) {
            //handle exception
        }
        return newAccountId != null;
    }
    public Account getAccountByUserId(int userId) {
        String sql = "SELECT user_id, account_id, balance FROM account WHERE user_id = ? ";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, userId);
        if (rowSet.next()) {
            return mapRowToAccount(rowSet);
        }
        return null;
    }
    @Override
    public void updateAccountFromBalance(int accountFrom, BigDecimal subtract) {
        String sql = "UPDATE accounts SET balance = balance - ? WHERE account_id = ?";
        Account updatedAccountToBalance = new Account();
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, subtract, accountFrom);
            if (results.next()) {
                updatedAccountToBalance = mapRowToAccount(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public void updateAccountToBalance(int accountTo, BigDecimal add) {
        String sql = "UPDATE accounts SET balance = balance + ? WHERE account_id = ?";
        Account updatedAccount = new Account();
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, add, accountTo);
            if (results.next()) {
                updatedAccount = mapRowToAccount(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public BigDecimal getBalance(int accountId) {
        Account account = new Account();
        String sql = "SELECT balance FROM account WHERE account_id= ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountId);
            while (results.next()) {
                account.setBalance(results.getBigDecimal("balance"));
            }
        } catch (CannotGetJdbcConnectionException e) {
            System.out.println(e.getMessage());
        }
        return account.getBalance();
    }
    private Account mapRowToAccount(SqlRowSet rs) {
        Account account = new Account();
        account.setUserID(rs.getInt("user_id"));
        account.setAccountID(rs.getInt("account_id"));
        account.setBalance(rs.getBigDecimal("balance"));
        return account;
    }
}


