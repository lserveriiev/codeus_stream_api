package december.tuesday.warmup;

import december.tuesday.helper_classes.Account;
import december.tuesday.helper_classes.Currency;
import december.tuesday.helper_classes.CurrentNbuRate;
import december.tuesday.helper_classes.Money;
import december.tuesday.helper_classes.User;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Exercises for improving Stream API knowledge.
 * <br> Warning!
 * <br> List of accounts {@link Account} and list of user {@link User} are not empty.
 */
public class StreamsWarmup {

    private static Predicate<Account> withCurrentMonthPredicate() {
        LocalDate now = LocalDate.now();

        return a ->
                (a.closingDate().getMonth().getValue() == now.getMonth().getValue())
                        && (a.closingDate().getYear() == now.getYear());
    }

    /**
     * Get int max raw amount {@link Money#amount()} of account's.
     * <br> Difficulty: EASY.
     *
     * @param accounts non-empty list of accounts.
     * @return max raw amount.
     */
    public int getMaxRawAmount(List<Account> accounts) {
        return accounts
                .stream()
                .mapToInt(a -> a.money().amount())
                .max()
                .orElseThrow();
    }

    /**
     * Get count of users with several types ({@link Currency}) of an account.
     * <br> Difficulty: EASY.
     *
     * @param accounts non-empty list of accounts.
     * @return Map with User's ID as key and List of Account's ID as value.
     */
    public Map<Integer, List<Integer>> getListOfIdAccountsByUserId(List<Account> accounts) {
        return accounts.stream()
                .collect(
                        Collectors.groupingBy(
                                Account::userId,
                                Collectors.mapping(Account::id, Collectors.toList())
                        )
                );
    }

    /**
     * Get the Total Sum UAH Equivalent For All Accounts.
     * <br> Difficulty: EASY.
     *
     * @param accounts non-empty list of {@link Account}.
     * @param rate     non-empty current rate by every {@link Currency}.
     * @return double total UAH equivalent.
     */
    public double getTotalUahEquivalentForAllAccounts(List<Account> accounts, CurrentNbuRate rate) {
        return accounts.stream()
                .mapToDouble(a -> a.money().getUahEquivalent(rate))
                .reduce(0.0, Double::sum);
    }

    /**
     * Get the oldest user from a list of Users.
     * <br> Difficulty: EASY.
     *
     * @param users non-empty list of {@link User}.
     * @return oldest user.
     */
    public User getOldestUser(List<User> users) {
        return users.stream()
                .max(Comparator.comparingInt(User::age))
                .orElseThrow();
    }

    /**
     * Get already closed account IDs.
     * <br> Difficulty: EASY.
     * <br> Note: use LocalDate.now().
     *
     * @param accounts non-empty list of {@link Account}.
     * @return list of account IDs.
     */
    public List<Integer> getClosedAccountIds(List<Account> accounts) {
        LocalDate now = LocalDate.now();

        return accounts.stream()
                .filter(a -> a.closingDate().isBefore(now))
                .map(Account::id)
                .toList();
    }

    /**
     * Get user IDs whose account will be closed this month.
     * <br> Difficulty: EASY.
     * <br> Note: use LocalDate.now().
     *
     * @param accounts non-empty list of {@link Account}.
     * @return Map with user ID as key and list of account IDs as value.
     */
    public Map<Integer, List<Integer>> getUsersIdWithAccountIdWillBeClosedAccount(List<Account> accounts) {
        return accounts.stream()
                .filter(a -> withCurrentMonthPredicate().test(a))
                .collect(
                        Collectors.groupingBy(
                                Account::userId,
                                Collectors.mapping(Account::id, Collectors.toList())
                        )
                );
    }

    /**
     * Get Map<Currency, Double> with max Big Amount {@link Money#getBigAmount()} by currency.
     * <br> Difficulty: MEDIUM.
     *
     * @param accounts non-empty list of accounts.
     * @return Map with Currency as the key and max Double Big Amount as value for the Currency.
     */
    public Map<Currency, Double> getMaxBigAmountByCurrency(List<Account> accounts) {
        return accounts.stream()
                .collect(
                        Collectors.toMap(
                                a -> a.money().currency(),
                                a -> a.money().getBigAmount(),
                                Math::max
                        )
                );
    }

    /**
     * Get a User who has max Big Amount values.
     * <br> Difficulty: MEDIUM.
     *
     * @param accounts list of Account.
     * @param users    list of User.
     * @return User if exists, else null.
     */
    public User getMaxBigAmountUser(List<Account> accounts, List<User> users) {
        int bigAmountUserId = accounts.stream()
                // .max((a, b) -> Double.compare(a.money().getBigAmount(), b.money().getBigAmount()))
                .max(Comparator.comparingDouble(a -> a.money().getBigAmount()))
                .map(Account::userId)
                .orElseThrow();

        return users.stream()
                .filter(u -> u.id() == bigAmountUserId)
                .findFirst()
                .orElse(null);
    }

    /**
     * Get users whose account will be closed this month.
     * <br> Difficulty: MEDIUM.
     * <br> Note: use LocalDate.now().
     *
     * @param accounts non-empty list of {@link Account}.
     * @param users    non-empty list of {@link User}.
     * @return Map with user as a key and list of accountID as a value.
     */
    public Map<User, List<Integer>> getUsersWithAccIDWillBeClosedAccount(List<Account> accounts,
                                                                         List<User> users) {
        Map<Integer, User> userIdToUser = users
                .stream()
                .collect(Collectors.toMap(User::id, u -> u));

        return accounts
                .stream()
                .filter(a -> withCurrentMonthPredicate().test(a))
                .collect(
                        Collectors.groupingBy(
                                a -> userIdToUser.get(a.userId()),
                                Collectors.mapping(Account::id, Collectors.toList())
                        )
                );

    }

    /**
     * Get count of users with several type ({@link Currency}) of account.
     * <br> Difficulty: HARD.
     *
     * @param accounts non-empty list of accounts.
     * @return count of users.
     */
    public int getCountOfUserWithSeveralTypeOfAccount(List<Account> accounts) {
        return (int) accounts.stream()
                .collect(
                        Collectors.groupingBy(
                                Account::userId,
                                Collectors.mapping(a -> a.money().currency(), Collectors.toSet())
                        )
                )
                .values()
                .stream()
                .filter(collection -> collection.size() > 1)
                .count();
    }

    /**
     * Get User with max sum total Big Amount in UAH Equivalent.
     * <br> Difficulty: HARD.
     *
     * @param accounts non-empty list of {@link Account}.
     * @param users    non-empty list of {@link User}.
     * @param rate     non-empty current rate by every {@link Currency}.
     * @return Map with User as key and total sum Big Amount in UAH Equivalent as value.
     */
    public Map<User, Double> getMaxBigAmountInUahEquivalent(List<Account> accounts,
                                                            List<User> users,
                                                            CurrentNbuRate rate) {
        Map<Integer, User> idToUser = users
                .stream()
                .collect(Collectors.toMap(User::id, u -> u));

        return accounts
                // to Map<userId, sum(amountInUah)>
                .stream()
                .collect(
                        Collectors.toMap(
                                Account::userId,
                                a -> a.money().getUahEquivalent(rate),
                                Double::sum
                        )
                )
                .entrySet()
                .stream()
                // find max amount
                .max(Comparator.comparingDouble(Map.Entry::getValue))
                .map(a -> Map.of(idToUser.get(a.getKey()), a.getValue()))
                .orElse(Map.of());
    }

}
