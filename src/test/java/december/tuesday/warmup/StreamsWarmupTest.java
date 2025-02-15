package december.tuesday.warmup;

import december.tuesday.helper_classes.Account;
import december.tuesday.helper_classes.Currency;
import december.tuesday.helper_classes.CurrentNbuRate;
import december.tuesday.helper_classes.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StreamsWarmupTest {
    private static List<User> users;
    private static List<Account> accounts;
    private static StreamsWarmup streamsWarmup;
    private static CurrentNbuRate currentNbuRate;

    @BeforeAll
    static void setUpBeforeClass() {
        streamsWarmup = new StreamsWarmup();

        users = new MakeUserList().getUsers();
        accounts = new MakeAccountList().getAccounts();

        var currencyMap = new HashMap<Currency, Double>();
        currencyMap.put(Currency.UAH, 1.0);
        currencyMap.put(Currency.USD, 41.85);
        currencyMap.put(Currency.EUR, 44.35);
        currentNbuRate = new CurrentNbuRate(currencyMap);
    }

    @Test
    @Order(1)
    @DisplayName("EASY: Max raw amount of account's")
    void checkMaxRawAmount() {
        var actual = streamsWarmup.getMaxRawAmount(accounts);
        var expected = 8690000;

        assertEquals(expected, actual);
    }

    @Test
    @Order(2)
    @DisplayName("EASY: List Of Account IDs By UserId")
    void checkListOfIdAccountsByUserId() {
        var actual = streamsWarmup.getListOfIdAccountsByUserId(accounts);
        var expected = new HashMap<Integer, List<Integer>>();
        expected.put(1, List.of(101, 110));
        expected.put(2, List.of(102));
        expected.put(3, List.of(103));
        expected.put(4, List.of(104));
        expected.put(5, List.of(105, 111));
        expected.put(6, List.of(106));
        expected.put(7, List.of(107));
        expected.put(8, List.of(108));
        expected.put(9, List.of(109, 112, 113));

        assertEquals(expected, actual);
    }

    @Test
    @Order(3)
    @DisplayName("EASY: Check Total Sum UAH Equivalent For All Accounts")
    void checkTotalSumUahEquivalentForAllAccounts() {
        var actual = streamsWarmup.getTotalUahEquivalentForAllAccounts(accounts, currentNbuRate);
        var expected = (640.0 + 4586.0 + 8564.0 + 45695.0 + 23500.0 + 25360.0 + 2560.0 + 23100.0 + 86900.0) +
                       (505.0 + 235.0 + 700.0) * 41.85 +
                       300.0 * 44.35;

        assertEquals(expected, actual);
    }

    @Test
    @Order(4)
    @DisplayName("EASY: Check the oldest user from list of Users")
    void checkOldestUserFromListOfUsers() {
        var expectedUserId = 6;
        var actual = streamsWarmup.getOldestUser(users);
        var expected = getExpectedUser(expectedUserId);

        assertEquals(expected, actual);
    }

    @Test
    @Order(5)
    @DisplayName("EASY: Check already closed account IDs")
    void checkAlreadyClosedAccountIDs() {
        var actual = streamsWarmup.getClosedAccountIds(accounts);
        var expected = List.of(102, 103, 105, 108);

        assertEquals(expected, actual);
    }

    @Test
    @Order(6)
    @DisplayName("EASY: Check user IDs whose account will be closed this month")
    void checkUserIDsWhoseAccountWillBeClosedThisMonth() {
        var actual = streamsWarmup.getUsersIdWithAccountIdWillBeClosedAccount(accounts);
        var expected = Map.of(5, List.of(111), 6, List.of(106));

        assertEquals(expected, actual);
    }

    @Test
    @Order(7)
    @DisplayName("MEDIUM: Max Big Amount of account's by currency")
    void checkMaxBigAmountByCurrency() {
        var actual = streamsWarmup.getMaxBigAmountByCurrency(accounts);
        var expected = new HashMap<Currency, Double>();
        expected.put(Currency.UAH, 86900.0);
        expected.put(Currency.USD, 700.0);
        expected.put(Currency.EUR, 300.0);

        assertEquals(expected, actual);
    }

    @Test
    @Order(8)
    @DisplayName("MEDIUM: Check the User with the largest Big Amount on the account")
    void maxAmountUser() {
        var expectedUserId = 9;
        var actual = streamsWarmup.getMaxBigAmountUser(accounts, users);
        var expected = getExpectedUser(expectedUserId);

        assertEquals(expected, actual);
    }

    @Test
    @Order(9)
    @DisplayName("MEDIUM: Check Users whose account will be closed this month")
    void checkUsersWithAccIDWillBeClosedAccount() {
        var actual = streamsWarmup.getUsersWithAccIDWillBeClosedAccount(accounts, users);
        var expected = Map.of(getExpectedUser(5), List.of(111),
                getExpectedUser(6), List.of(106));

        assertEquals(expected, actual);
    }

    @Test
    @Order(10)
    @DisplayName("HARD: Counting of users with several type of account")
    void checkCountOfUsersWithSeveralTypeOfAccount() {
        var actual = streamsWarmup.getCountOfUserWithSeveralTypeOfAccount(accounts);
        var expected = 3;

        assertEquals(expected, actual);
    }

    @Test
    @Order(11)
    @DisplayName("HARD: Check max Big Amount in UAH equivalent")
    void checkMaxBigAmountInUahEquivalent() {
        var expectedUserId = 9;
        var expectedUser = getExpectedUser(expectedUserId);
        var expectedUahEquivalent = 86900 + (700 * 41.85) + (300 * 44.35);

        var actual = streamsWarmup.getMaxBigAmountInUahEquivalent(accounts, users, currentNbuRate);
        var expected = Map.of(expectedUser, expectedUahEquivalent);

        assertEquals(expected, actual);
    }

    private User getExpectedUser(int expectedUserId) {
        return users.stream().filter(u -> u.id() == expectedUserId).findFirst().get();
    }

}
