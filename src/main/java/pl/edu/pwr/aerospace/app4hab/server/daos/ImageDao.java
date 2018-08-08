package pl.edu.pwr.aerospace.app4hab.server.daos;

import com.sun.jersey.api.NotFoundException;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import pl.edu.pwr.aerospace.app4hab.server.entities.Image;

import java.util.List;

public class ImageDao extends Dao {

    public Image getImage(int ID){
        Session s = Db.getSession();
        Transaction tx = s.beginTransaction();
        Criteria c = s.createCriteria(Image.class)
                .add(Restrictions.like("ID", ID))
                .addOrder(Order.desc("ID"));

        try {
            return (Image)c.list().get(0);
        }catch (IndexOutOfBoundsException e){
            throw new NotFoundException();
        }
    }

    public List<Image> getAllImages(){
        Session s = Db.getSession();
        Transaction tx = s.beginTransaction();
        Criteria c = s.createCriteria(Image.class);
        c.addOrder(Order.desc("ID"));
        return c.list();
    }
}
