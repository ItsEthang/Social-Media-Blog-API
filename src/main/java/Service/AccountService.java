package Service;

import java.util.List;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    public AccountDAO accountDAO;

    public AccountService() {
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public List<Account> getAllAccounts() {
        return this.accountDAO.getAllAccounts();
    }

    // Account register service
    public Account accountRegister(Account account) {
        String username = account.getUsername();
        String password = account.getPassword();

        if (username.isBlank() || password.length() < 4) {
            return null;
        }

        List<Account> allAccounts = this.getAllAccounts();

        for (Account account1 : allAccounts) {
            if (account1.getAccount_id() == account.getAccount_id()) {
                return null;
            }
        }

        return this.accountDAO.insertAccount(account);
    }

    // Account login
    public Account accountLogin(Account account) {
        return this.accountDAO.accountLogin(account);
    }
}
