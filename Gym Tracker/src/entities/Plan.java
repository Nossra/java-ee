package entities;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import utilities.EntityUtil;
import utilities.HibernateUtil;
import viewmodel.PlanInfoViewModel;

@Entity
public class Plan {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private int timesPerWeek;
	@ManyToOne
	@JoinColumn(name="user_id")	
	private User users;	
	@OneToMany(mappedBy = "plan", cascade = CascadeType.ALL)
	private List<Day> days = new ArrayList<Day>();
	
	public Plan() {
		
	}
	public Plan(String name) {
		this.name = name;
	}
	public Plan(String name, int timesPerWeek, User user) {
		this.setName(name);
		this.setTimesPerWeek(timesPerWeek);
		this.setUsers(user);	
		for (int i = 0; i < this.getTimesPerWeek(); i++) {
			days.add(new Day(i+1, this));
		}	
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getTimesPerWeek() {
		return timesPerWeek;
	}
	public void setTimesPerWeek(int timesPerWeek) {
		this.timesPerWeek = timesPerWeek;
	}

	public User getUsers() {
		return users;
	}

	public void setUsers(User users) {
		this.users = users;
	}

	public List<Day> getDays() {
		return days;
	}

	public void setDays(List<Day> days) {
		this.days = days;
	}
	
	public List<Day> selectDays() {
		Session session = HibernateUtil.getSessionFactory().openSession();

		String HQL_SELECT_DAYS = "SELECT d FROM Plan p, Day d WHERE p.id=d.plan AND p.name = '" + this.getName() + "'";
		
		@SuppressWarnings("unchecked")
		List<Day> result = (List<Day>) session.createQuery(HQL_SELECT_DAYS).list();
		
		if (result.size() == 0) {
			System.out.println("Days are empty.");
		} else {
			return result;		
		}
		session.close();
		return result;
	}
	
	public void addExercise(Exercise exercise, int dayNr, double weight, int repetitions) {
		SetInfo sets = new SetInfo(weight, repetitions);
		EntityUtil.save(sets);
		DAY_EXERCISES de = new DAY_EXERCISES(this.selectDays().get(dayNr-1), exercise, sets);		
		EntityUtil.save(de);
	}
	
	public void selectFullPlanInfo() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		String HQL_SELECT_FULL_PLAN = "SELECT new viewmodel.PlanInfoViewModel(p.name, d.dayNr, e.name, si.weight, si.repetitions) "
				+"FROM Day d, SetInfo si, DAY_EXERCISES de, Exercise e, Plan p "
				+"WHERE de.day=d.id AND e.id=de.exercises AND si.id=de.sets AND p.id=d.plan AND p.name = '" + this.getName() + "'";	
		
		@SuppressWarnings("unchecked")
		List<PlanInfoViewModel> result = (List<PlanInfoViewModel>) session.createQuery(HQL_SELECT_FULL_PLAN).list();

		for (int i = 0; i < result.size(); i++) {
			PlanInfoViewModel vm = result.get(i);
			Plan p = new Plan(vm.getPlanName());
			Day d = new Day(vm.getDayNr());
			Exercise e = new Exercise(vm.getExerciseName());
			SetInfo siWeight = new SetInfo(vm.getWeight(), vm.getRepetitions());
			
			if (result.size() == 0) {
				System.out.println("Plan is empty.");
			} else {
				System.out.println(p.getName() + " " + d.getDayNr() + " " + e.getName() + " " + siWeight.getWeight() + " " + siWeight.getRepetitions());
			}
		}
		session.close();
	}
	
	public void selectDayInfo(int day) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		String HQL_DAY_INFO = "SELECT new viewmodel.PlanInfoViewModel(p.name, d.dayNr, e.name, si.weight, si.repetitions) "
					+"FROM Day d, SetInfo si, DAY_EXERCISES de, Exercise e, Plan p "
					+"WHERE de.day=d.id AND e.id=de.exercises AND si.id=de.sets AND p.id=d.plan AND d.dayNr = :dayNr AND p.name = '" + this.getName() + "'";
		
		@SuppressWarnings("unchecked")
		List<PlanInfoViewModel> result = (List<PlanInfoViewModel>) session.createQuery(HQL_DAY_INFO)
				.setParameter("dayNr", day)
				.list();		
		
		for (int i = 0; i < result.size(); i++) {
			PlanInfoViewModel vm = result.get(i);
			Plan p = new Plan(vm.getPlanName());
			Day d = new Day(vm.getDayNr());
			Exercise e = new Exercise(vm.getExerciseName());
			SetInfo siWeight = new SetInfo(vm.getWeight(), vm.getRepetitions());
			
			if (result.size() == 0) {
				System.out.println("Plan is empty.");
			} else {
				System.out.println(p.getName() + " " + d.getDayNr() + " " + e.getName() + " " + siWeight.getWeight() + " " + siWeight.getRepetitions());
			}
		}
		session.close();
	}
}
