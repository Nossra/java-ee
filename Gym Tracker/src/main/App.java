package main;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import entities.Exercise;
import entities.Exercise.Muscle;
import entities.User;
import entities.DAY_EXERCISES;
import entities.Day;
import entities.Plan;
import entities.SetInfo;
import utilities.EntityUtil;
import utilities.HibernateUtil;

public class App {

	public static void main(String[] args) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		
//		Exercise e = new Exercise("Hantel Curl", Muscle.BICEPS, "Biceps�vning d�r man lyfter hantlar");
//		Exercise e1 = new Exercise("B�nkpress", Muscle.BR�ST, "Br�st�vning");
//		Exercise e2 = new Exercise("Skivst�ngs rodd", Muscle.RYGG, "Br�st�vning st�ende");
//		Exercise e3 = new Exercise("Kn�b�j", Muscle.L�R, "Tung bas�vning f�r ben som tr�ffar st�rre delar utav kroppen");
//		Exercise e4 = new Exercise("Plankan", Muscle.MAGE, "Mag�vning f�r statisk tr�ning");
		
		User martin = new User("Martin", "Nosslin", "1234");
		Plan p1 = new Plan("Get ripped", 3, martin);
		Plan p2 = new Plan("Get swole", 2, martin);
		
//		EntityUtil.saveAll(e, e1, e2, e3, e4);
//		EntityUtil.save(martin);
//		EntityUtil.saveAll(p1, p2);
		
//		p1.addExercise(Exercise.selectAll().get(2), 1, 80, 5);
		
//		p1.addExercise(Exercise.selectAll().get(2), 1, 80, 4);
//		p1.addExercise(Exercise.selectAll().get(2), 1, 75, 5);
//		
//		p1.addExercise(Exercise.selectAll().get(3), 1, 100, 5);
//		p1.addExercise(Exercise.selectAll().get(3), 1, 100, 4);
//		p1.addExercise(Exercise.selectAll().get(3), 1, 95, 5);
//		
//		p1.addExercise(Exercise.selectAll().get(4), 2, 10, 6);
//		p1.addExercise(Exercise.selectAll().get(4), 2, 10, 5);
//		p1.addExercise(Exercise.selectAll().get(4), 2, 10, 4);
//		
//		p1.addExercise(Exercise.selectAll().get(0), 2, 26, 7);
//		p1.addExercise(Exercise.selectAll().get(0), 2, 26, 6);
//		p1.addExercise(Exercise.selectAll().get(0), 2, 24, 5);
//		
//		p2.addExercise(Exercise.selectAll().get(1), 1, 110, 6);
//		p2.addExercise(Exercise.selectAll().get(1), 1, 110, 5);
//		p2.addExercise(Exercise.selectAll().get(1), 1, 105, 4);
//		p2.addExercise(Exercise.selectAll().get(1), 1, 100, 5);
//		
//		p2.addExercise(Exercise.selectAll().get(0), 1, 26, 7);
//		p2.addExercise(Exercise.selectAll().get(0), 1, 26, 6);
//		p2.addExercise(Exercise.selectAll().get(0), 1, 24, 5);
//		
//		p2.addExercise(Exercise.selectAll().get(2), 2, 67, 6);
//		p2.addExercise(Exercise.selectAll().get(2), 2, 67, 5);
//		p2.addExercise(Exercise.selectAll().get(2), 2, 67, 4);
//		
//		p2.addExercise(Exercise.selectAll().get(3), 2, 30, 7);
//		p2.addExercise(Exercise.selectAll().get(3), 2, 35, 6);
//		p2.addExercise(Exercise.selectAll().get(3), 2, 40, 5);
//		
//		System.out.println("Plan 1 full info");
//		p1.selectFullPlanInfo();
//		System.out.println("\n\n\n");
//		System.out.println("Plan 2 full info");
//		p2.selectFullPlanInfo();
//		System.out.println("\n\n\n");
//		System.out.println("Plan 1 day 1");
//		p1.selectDayInfo(1);
//		System.out.println("\n\n\nPlan 2 day 2");
//		p2.selectDayInfo(2);
//		p2.selectFullPlanInfo();
		

		
		session.close();
		System.out.println("\nfinished");
	}
	

}


