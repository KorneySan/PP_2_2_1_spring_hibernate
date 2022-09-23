package hiber.service;

import hiber.dao.UserDao;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.List;

@Service
public class UserServiceImp implements UserService {

   private UserDao userDao;

   private SessionFactory sessionFactory;

   @Autowired
   public UserServiceImp(UserDao userDao, SessionFactory sessionFactory) {
      this.userDao = userDao;
      this.sessionFactory = sessionFactory;
   }

   @Transactional
   @Override
   public void add(User user) {
      userDao.add(user);
   }

   @Transactional(readOnly = true)
   @Override
   public List<User> listUsers() {
      return userDao.listUsers();
   }

   @Transactional(readOnly = true)
   @Override
   public User getUserByCar(String model, int series) {
      TypedQuery<User> queryUser = sessionFactory.getCurrentSession().createQuery("from User u where u.id in (select c.id from Car c where (model = :model AND series = :series))");
      queryUser.setParameter("model", model);
      queryUser.setParameter("series", series);
      return queryUser.getSingleResult();
   }
}
