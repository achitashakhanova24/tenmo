package com.techelevator.dao;


import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class JdbcUserDaoTests extends BaseDaoTests {

    private JdbcUserDao sut;
    private Account account;

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcUserDao(jdbcTemplate);
    }

    @Test
    public void createNewUser() {
        boolean userCreated = sut.create("TEST_USER", "test_password");
        Assert.assertTrue(userCreated);
        User user = sut.findByUsername("TEST_USER");
        Assert.assertEquals("TEST_USER", user.getUsername());
    }

    //    REGISTER/LOGIN:
    //            *can't register twice with same username
    @Test
    public void testDuplicateUsername() {
        sut.create("TEST_USER", "test_password");
        boolean userCreated = sut.create("TEST_USER", "test_password");
        Assert.assertEquals(true, userCreated);
    }
}
    //            *ERROR on accessing any endpoint besides login & register without a token
    // @Test
    //    public void testTokenValidation(){
    //        List<User> users = sut.findAll();
    //        Assert.assertNotNull(users);
    //        Assert.assertTrue(users.size() > 0);
    //
    //        User user = users.get(0);
    //        Assert.assertNotNull(user);
    //
    //        boolean isValidToken = sut.isTokenValid(user.getToken());
    //        Assert.assertTrue(isValidToken);
    //
    //        isValidToken= sut.is("invalid-token");
    //        Assert.assertFalse(isValidToken);
    // }

    //*ERROR on login with incorrect credentials/token
    //*ERROR on login without registering
    //*all registered users (username only) can be viewed
    //*get correct initial balance ($1000)
    //*ERROR on endpoint path contains account #, balance, transfer info
    //*ERROR on getting balance of a user other than principal
    //*ERROR on getting transactions of a user other than principal
    //*ERROR on getting transaction id of a user other than principal
    //SEND TRANSACTIONS:
    //*correct amount transferred w/ different values
    //*correct balance after sending transfers
    //*correct balance after sending floating point values, e.g. $0.01
    //- floating point miscalculations, BigDecimal used.
    //*Transaction Status automatically approved
    //*Each transaction logged: sender, receiver, status, amount, timestamp (optional)
    //*ERROR on getting balance of a user other than principal
    //*ERROR on blank receiver from client request
    //*ERROR on unknown receiver from client request
    //*ERROR on same receiver as receiver from client request
    //*ERROR on blank amount from client request
    //*ERROR on negative amount from client request
    //*ERROR on 0 amount from client request
    //*ERROR on sending invalid float point values, e.g. $0.001
    //*ERROR on send amount more than balance from client request (unsufficient funds)
    //*ERROR on sender not matching username from client request
    //RECEIVE TRANSACTIONS:
    //*Requestor can cancel transaction
    //*Sender can cancel transaction
    //*Sender can approve transaction
    //- Balances updated upon transaction approval by sender
    //*Sender can cancel transaction
    //- Balances NOT updated
    //*One transaction logged while switching transaction status: sender, receiver, status, amount, timestamp (optional)
    //*ERROR on request from invalid/unknown sender
    //*ERROR on blank amount from client request
    //*ERROR on negative amount from client request
    //*ERROR on 0 amount from client request
    //*ERROR on sending invalid float point values, e.g. $0.001
    //*ERROR on same receiver and sender from client request
    //*ERROR on receiver updating transaction status
    //*ERROR on sender APPROVE with insufficient funds
