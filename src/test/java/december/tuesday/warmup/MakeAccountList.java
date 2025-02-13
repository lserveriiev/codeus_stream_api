package december.tuesday.warmup;

import december.tuesday.helper_classes.Account;
import december.tuesday.helper_classes.Currency;
import december.tuesday.helper_classes.Money;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MakeAccountList {
    List<Account> accounts;

    public MakeAccountList() {
        accounts = new ArrayList<>();
        setAccounts();
    }

    private void setAccounts() {
        LocalDate now = LocalDate.now();
        var account1 = new Account(101, 1,
                new Money(Currency.UAH, 64000),
                now.minusYears(1),
                now.plusYears(1));
        var account2 = new Account(102, 2,
                new Money(Currency.UAH, 458600),
                now.minusYears(3),
                now.minusMonths(6));
        var account3 = new Account(103, 3,
                new Money(Currency.UAH, 856400),
                now.minusYears(2),
                now.minusMonths(4)
        );
        var account4 = new Account(104, 4,
                new Money(Currency.UAH, 4569500),
                now.minusMonths(2),
                now.plusMonths(6)
        );
        var account5 = new Account(105, 5,
                new Money(Currency.UAH, 2350000),
                now.minusMonths(2),
                now.minusMonths(10)
        );
        var account6 = new Account(106, 6,
                new Money(Currency.UAH, 2536000),
                now.minusYears(2),
                LocalDate.of(now.getYear(), now.getMonth(), 19)
        );
        var account7 = new Account(107, 7,
                new Money(Currency.UAH, 256000),
                now.minusMonths(6),
                now.plusYears(2)
        );
        var account8 = new Account(108, 8,
                new Money(Currency.UAH, 2310000),
                now.minusYears(2),
                now.minusMonths(6)
        );
        var account9 = new Account(109, 9,
                new Money(Currency.UAH, 8690000),
                now.minusMonths(6),
                now.plusMonths(6)
        );
        var account10 = new Account(110, 1,
                new Money(Currency.USD, 50500),
                now,
                now.plusYears(1)
        );
        var account11 = new Account(111, 5,
                new Money(Currency.USD, 23500),
                now.minusYears(4),
                now
        );
        var account12 = new Account(112, 9,
                new Money(Currency.USD, 70000),
                now.minusYears(1),
                now.plusYears(2)
        );
        var account13 = new Account(113, 9,
                new Money(Currency.EUR, 30000),
                now.minusMonths(10),
                now.plusYears(2)
        );

        accounts.add(account1);
        accounts.add(account2);
        accounts.add(account3);
        accounts.add(account4);
        accounts.add(account5);
        accounts.add(account6);
        accounts.add(account7);
        accounts.add(account8);
        accounts.add(account9);
        accounts.add(account10);
        accounts.add(account11);
        accounts.add(account12);
        accounts.add(account13);
    }

    public List<Account> getAccounts() {
        return accounts;
    }
}
