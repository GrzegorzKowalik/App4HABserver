package pl.edu.pwr.aerospace.app4hab.server.daos;

import com.sun.jersey.api.NotFoundException;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import pl.edu.pwr.aerospace.app4hab.server.entities.PhoneActivity;

import java.util.List;

public class PhoneActivityDao extends Dao {

    public PhoneActivity getLastActivity(){
        Session s = Db.getSession();
        Transaction tx = s.beginTransaction();
        Criteria c = s.createCriteria(PhoneActivity.class);
        c.addOrder(Order.desc("timestamp"));
        c.setFirstResult(0);
        c.setMaxResults(1);

        try {
            return (PhoneActivity)c.list().get(0);
        }catch (IndexOutOfBoundsException e){
            throw new NotFoundException();
        }
    }

    public List<PhoneActivity> getAllActivities(){
        Session s = Db.getSession();
        Transaction tx = s.beginTransaction();
        Criteria c = s.createCriteria(PhoneActivity.class);
        c.addOrder(Order.desc("timestamp"));
        c.setFirstResult(0);
        c.setMaxResults(150);
        return c.list();
    }

    public PhoneActivity getActivity(int id){
        Session s = Db.getSession();
        Transaction tx = s.beginTransaction();
        Criteria c = s.createCriteria(PhoneActivity.class)
                .add(Restrictions.like("id", id));

        try {
            return (PhoneActivity)c.list().get(0);
        }catch (IndexOutOfBoundsException e){
            throw new NotFoundException();
        }
    }

    public List<PhoneActivity> getLogs() {
        Session s = Db.getSession();
        Transaction tx = s.beginTransaction();
        Criteria c = s.createCriteria(PhoneActivity.class)
                .add(Restrictions.like("endpoint", "log"));
        c.addOrder(Order.desc("timestamp"));
        return c.list();
    }
}
