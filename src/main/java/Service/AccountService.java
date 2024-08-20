package Service;

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

    // Account register service
    public Account accountRegister(Account account) {
        String username = account.getUsername();
        String password = account.getPassword();
        Account accountByUser = accountDAO.getAccountByUsername(username);
        boolean isValidAccount = username.length() > 0 && password.length() > 3 && accountByUser == null;

        if (isValidAccount)
            return this.accountDAO.insertAccount(account);
        return null;
    }

    // Account login
    public Account accountLogin(Account account) {
        return this.accountDAO.accountLogin(account);
    }
}
