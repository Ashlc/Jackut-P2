package br.ufal.ic.p2.jackut.system;
import java.util.ArrayList;


public class UserList extends ArrayList<User> implements PrintAll {

    public UserList() {
        super();
    }

    @Override
    public String printAll() {

        if(this.isEmpty()) {
            return "{}";
        }

        StringBuilder sb = new StringBuilder();
        sb.append('{');

        for (User user : this) {
            sb.append(user.getLogin());
            sb.append(',');
        }

        if (sb.length() > 1) {
            sb.deleteCharAt(sb.length() - 1);
        }

        sb.append('}');

        return sb.toString();
    }
}
