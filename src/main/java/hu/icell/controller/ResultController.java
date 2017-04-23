package hu.icell.controller;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import hu.icell.dao.ResultDao;
import hu.icell.entities.Result;
import hu.icell.entities.User;

@Controller
@RequestMapping(value = "/result")
public class ResultController {
	
	@Inject
	private ResultDao resultDao;
	
//	@PersistenceUnit(unitName="memorygame")
//	private EntityManagerFactory emf;
//  private EntityManager em;
	
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView listAction(HttpServletRequest request) {
		
//      emf = Persistence.createEntityManagerFactory("memorygame");
//	    em = emf.createEntityManager();
//	    em.getTransaction().begin();
//	    Result r = new Result();
//	    r.setResultDate(Calendar.getInstance().getTime());
//	    r.setSeconds(BigDecimal.valueOf(55));
//	    r.setUser(em.find(User.class, 1L));
//	    em.persist(r);
//	    em.flush();
//	    Query q = em.createQuery("SELECT r FROM Result r JOIN FETCH r.user u ORDER BY r.seconds ASC, r.resultDate DESC");
//	    q.setMaxResults(20);
//	    List<Result> list = q.getResultList();
	    
	    HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            return new ModelAndView("redirect:/login");
        }
	    
	    List<Result> list = resultDao.getResults();
//	    em.getTransaction().commit();
		
		ModelAndView model = new ModelAndView("result/results");
		model.addObject("list", list);
	
		return model;

	}
	
	@RequestMapping(value = "/{userId:\\d+}", method = RequestMethod.GET)
	public ModelAndView showAction(HttpServletRequest request, @PathVariable("userId") String userId) {
	    
	    HttpSession session = request.getSession(false);
	    User user;
        if (session == null || (user = (User)session.getAttribute("user")) == null || user.getId() != Long.parseLong(userId)) {
            return new ModelAndView("redirect:/login");
        }
	    List<Result> list = resultDao.getResultsByUser(user);
		ModelAndView model = new ModelAndView("result/userresult");
		model.addObject("list", list);
	
		return model;

	}
	
}
