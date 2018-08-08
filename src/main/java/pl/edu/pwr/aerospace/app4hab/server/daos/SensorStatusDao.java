package pl.edu.pwr.aerospace.app4hab.server.daos;

import com.sun.jersey.api.NotFoundException;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import pl.edu.pwr.aerospace.app4hab.server.entities.SensorStatus;

import java.util.List;

public class SensorStatusDao extends Dao {

    public SensorStatus getStatus(int ID){
        Session s = Db.getSession();
        Transaction tx = s.beginTransaction();
        Criteria c = s.createCriteria(SensorStatus.class)
                .add(Restrictions.like("ID", ID))
                .addOrder(Order.desc("ID"));

        try {
            return (SensorStatus)c.list().get(0);
        }catch (IndexOutOfBoundsException e){
            throw new NotFoundException();
        }
    }

    public List<SensorStatus> getAllStatuses(){
        Session s = Db.getSession();
        Transaction tx = s.beginTransaction();
        Criteria c = s.createCriteria(SensorStatus.class);
        c.addOrder(Order.desc("ID"));
        return c.list();
    }
}
