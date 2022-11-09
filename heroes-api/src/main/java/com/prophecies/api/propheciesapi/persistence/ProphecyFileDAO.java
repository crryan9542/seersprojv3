package com.prophecies.api.propheciesapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prophecies.api.propheciesapi.model.Prophecy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ProphecyFileDAO implements ProphecyDAO{
private static final Logger LOG = Logger.getLogger(ProphecyFileDAO.class.getName());
Map<Integer,Prophecy> prophecies;   // Provides a local cache of the Prophecy objects
                                // so that we don't need to read from the file
                                // each time
private ObjectMapper objectMapper;  // Provides conversion between Prophecy
                                    // objects and JSON text format written
                                    // to the file

private static int nextId;  // The next Id to assign to a new prophecy

private String filename;    // Filename to read from and write to

public ProphecyFileDAO(@Value("${prophecies.file}") String filename,ObjectMapper objectMapper) throws IOException {
    this.filename = filename;
    this.objectMapper = objectMapper;
    load();  // load the prophecies from the file
}
private synchronized static int nextId() {
    int id = nextId;
    ++nextId;
    return id;
}

private Prophecy[] getProphecyArray() {
    return getProphecyArray(null);
}


private Prophecy[] getProphecyArray(String containstext) {
    ArrayList<Prophecy> prophecyArrayList = new ArrayList<>();

    for (Prophecy prophecy : prophecies.values()) {
        if (containstext == null || prophecy.getName().contains(containstext)) {
            prophecyArrayList.add(prophecy);
        }
    }

    Prophecy[] prophecyArray = new Prophecy[prophecyArrayList.size()];
    prophecyArrayList.toArray(prophecyArray);
    return prophecyArray;
}
    
private boolean save() throws IOException{
    Prophecy[] prophecyArray = getProphecyArray();
    objectMapper.writeValue(new File(filename), prophecyArray);
    return true;
}


private boolean load() throws IOException{
    prophecies = new TreeMap<>();
    nextId = 0;
    Prophecy[] prophecyArray = objectMapper.readValue(new File(filename), Prophecy[].class);
    for (Prophecy prophecy : prophecyArray) {
        prophecies.put(prophecy.getId(), prophecy);
        if (prophecy.getId() >= nextId) {
            nextId = prophecy.getId() + 1;
        }
    }
    ++nextId;
    return true;
}

    @Override
    public Prophecy[] getProphecies() {
        synchronized (prophecies) {
            return getProphecyArray();
        }
    }

    @Override
    public Prophecy[] findProphecies(String name) {
        synchronized (prophecies) {
            return getProphecyArray(name);
        }
    }

    @Override
    public Prophecy getProphecy(int id){
        synchronized (prophecies) {
            if(prophecies.containsKey(id)){
                return prophecies.get(id);
            }else{
                return null;
            }
        }
    }

    @Override
    public Prophecy createProphecy(Prophecy prophecy) throws IOException {
        synchronized(prophecies){
            Prophecy newProphecy = new Prophecy(prophecy.getId(),prophecy.getName(),prophecy.getDescription(), prophecy.getPrice(), prophecy.getQuantity());
            prophecies.put(newProphecy.getId(), newProphecy);
            save();
            return newProphecy;
        }
    }

    @Override
    public Prophecy updateProphecy(Prophecy prophecy) throws IOException {
        synchronized(prophecies){
            if(prophecies.containsKey(prophecy.getId())){
                prophecies.put(prophecy.getId(), prophecy);
                save();
                return prophecy;
            }else{
                return null;
            }
        }
    }

    @Override
    public boolean deleteProphecy(int id) throws IOException {
        synchronized(prophecies){
            if(prophecies.containsKey(id)){
                prophecies.remove(id);
                save();
                return true;
            }else{
                return false;
            }
        }
    }
    
}
