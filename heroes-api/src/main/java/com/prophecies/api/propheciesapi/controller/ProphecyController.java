package com.prophecies.api.propheciesapi.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.prophecies.api.propheciesapi.model.Prophecy;
import com.prophecies.api.propheciesapi.persistence.ProphecyDAO;


@RestController
@RequestMapping("prophecies")
public class ProphecyController {
private static final Logger LOG = Logger.getLogger(ProphecyController.class.getName());
private ProphecyDAO prophecyDao;

public ProphecyController(ProphecyDAO prophecyDao) {
    this.prophecyDao = prophecyDao;
}

@GetMapping("/{id}")
public ResponseEntity<Prophecy> getProphecy(@PathVariable("id") int id) {
    try {
        Prophecy prophecy = prophecyDao.getProphecy(id);
        if (prophecy != null) {
            return new ResponseEntity<>(prophecy, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    } catch (IOException ex) {
        LOG.log(Level.SEVERE, "Error getting prophecy", ex);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

@GetMapping("")
public ResponseEntity<Prophecy[]> getProphecies(){
    LOG.info("GET /prophecies");
    try {
        Prophecy[] prophecies = prophecyDao.getProphecies();
        return new ResponseEntity<>(prophecies, HttpStatus.OK);
    } catch (IOException ex) {
        LOG.log(Level.SEVERE, "Error getting prophecies", ex);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

@PostMapping("/")
public ResponseEntity<Prophecy[]> searchProphecies(@RequestParam String name){
    LOG.info("GET /prophecies/?name=" + name);
    try{
        return new ResponseEntity<Prophecy[]>(prophecyDao.findProphecies(name), HttpStatus.OK);
    } catch (IOException ex) {
        LOG.log(Level.SEVERE, "Error getting the specific prophecy", ex);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

@PostMapping("")
public ResponseEntity<Prophecy> createProphecy(@RequestBody Prophecy prophecy) {
    LOG.info("POST /prophecies" + prophecy);
    try {
        Prophecy[] curProphecies = prophecyDao.getProphecies();
        boolean alreadyPresent = Arrays.asList(curProphecies).contains(prophecy);
    if(alreadyPresent){
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }else{
        return new ResponseEntity<>(prophecyDao.createProphecy(prophecy), HttpStatus.CREATED);
    }
    } catch (IOException ex) {
        LOG.log(Level.SEVERE, "Error creating prophecy", ex);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

@PutMapping("")
public ResponseEntity<Prophecy> updateProphecy(@RequestBody Prophecy prophecy) {
    LOG.info("PUT /prophecies" + prophecy);
    try {
        ArrayList<Prophecy> lst = new ArrayList<>(Arrays.asList(prophecyDao.getProphecies()));

        if (!lst.contains(prophecy)) {
            prophecy = prophecyDao.updateProphecy(prophecy);
            return new ResponseEntity<Prophecy>(prophecy,HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    catch(IOException e) {
        LOG.log(Level.SEVERE,e.getLocalizedMessage());
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
@DeleteMapping("/{id}")
    public ResponseEntity<Prophecy> deleteProphecy(@PathVariable int id) {
        LOG.info("DELETE /prophecyes/" + id);

        // Implementation
        try {
            Prophecy prophecy = prophecyDao.getProphecy(id);
            ArrayList<Prophecy> lst = new ArrayList<>(Arrays.asList(prophecyDao.getProphecies()));

            if (lst.contains(prophecy)) {
                prophecyDao.deleteProphecy(id);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
