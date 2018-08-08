package pl.edu.pwr.aerospace.app4hab.server.daos;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import pl.edu.pwr.aerospace.app4hab.server.entities.Commands;

public class CommandsDao extends Dao {

    /**
     * @return latest commands for phone
     */
    public Commands getLatestCommands(){
        Session s = Db.getSession();
        Transaction tx = s.beginTransaction();
        Criteria c = s.createCriteria(Commands.class);
        c.addOrder(Order.desc("ID"));

        try {
            return (Commands)c.list().get(0);
        }catch (IndexOutOfBoundsException e){
            return new Commands();
        }
    }
}
