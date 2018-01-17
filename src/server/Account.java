package server;

import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by Hamed-Abbaszadeh on 1/12/2018.
 */
public class Account
{
    private String user;
    private String pass;

    public Account(String user, String pass)
    {
        this.user = user;
        this.pass = pass;
    }

    public String getUser()
    {
        return user;
    }

    public String getPass()
    {
        return pass;
    }


    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        if (account.getPass().equals(this.pass) && account.getUser().equals(this.user))
        {
            return true;
        }
        else
            return false;
    }

}
