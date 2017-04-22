package hu.icell.controller;

import java.util.HashMap;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hu.icell.dao.ResultDao;
import hu.icell.entities.User;

@RestController
@RequestMapping(value = "/result")
public class ResultRestController {
    @PersistenceContext(unitName="memorygame")
    private EntityManager em;
    
    @Inject
    private ResultDao resultDao;
    
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public HashMap<String, Object> saveAction(HttpServletRequest request,  @RequestParam(name="userId", required=false) Long userId,
            @RequestParam(name="seconds", required=false) Integer seconds) {
        
        HashMap<String, Object> map = new HashMap<>();
        map.put("seconds", seconds);
        HttpSession session = request.getSession(false);
        User user;
        if (userId == null || seconds == null || session == null || (user = (User)session.getAttribute("user")) == null || user.getId() != userId) {
            map.put("success", "error");
            return map;
        }
        resultDao.saveResult(seconds, userId);
        map.put("success", "success");
        
        return map;
    }
    
//    @RequestMapping(value = "/save", method = RequestMethod.POST)
//    public ResponseEntity<HashMap<String, Object>> saveAction(HttpServletRequest request,  @RequestParam(name="userId", required=false) Long userId,
//            @RequestParam(name="seconds", required=false) Integer seconds) {
//        
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("seconds", seconds);
//        HttpSession session = request.getSession();
//        User user;
//        if (userId == null || seconds == null || session == null || (user = (User)session.getAttribute("user")) == null || user.getId() != userId) {
//            map.put("success", "error");
//            return new ResponseEntity<HashMap<String,Object>>(map, HttpStatus.OK);
//        }
//        resultDao.saveResult(seconds, userId);
//        map.put("success", "success");
//        
//        return new ResponseEntity<HashMap<String,Object>>(map, HttpStatus.OK);
//    }
    
}
