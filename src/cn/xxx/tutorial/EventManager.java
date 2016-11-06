package cn.xxx.tutorial;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import cn.xxx.domain.Event;
import cn.xxx.domain.Person;
import cn.xxx.utils.HibernateUtils;

public class EventManager {

	public static void main(String[] args) {
		EventManager manager = new EventManager();
//		manager.createAndStoreEvent("store", new Date());
		List list = manager.listEvents();
		for (Object object : list) {
			Event event = (Event)object;
			System.out.println("Event: "+event.getTitle()
			+"	Time: "+event.getDate());
		}
		HibernateUtils.getSessionFactory().close();
	}

	private void createAndStoreEvent(String title, Date date) {
		Session session = HibernateUtils.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		Event event = new Event();
		event.setTitle(title);
		event.setDate(date);
		session.save(event);

		session.getTransaction().commit();
	}

	public List listEvents() {
		Session session = HibernateUtils.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List result = session.createQuery("from Event").list();
		session.getTransaction().commit();
		return result;
	}
	
	public void addPersonToEvent(Long personId,Long eventId){
		Session session = HibernateUtils.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		Person person = (Person) session.load(Person.class, personId);
		Event event = (Event) session.load(Event.class, eventId);
		person.getEvents().add(event);
		
		session.getTransaction().commit();
	}
}
