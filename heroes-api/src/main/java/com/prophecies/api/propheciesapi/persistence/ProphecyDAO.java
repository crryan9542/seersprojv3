    package com.prophecies.api.propheciesapi.persistence;
import java.io.IOException;
import com.prophecies.api.propheciesapi.model.Prophecy;

public interface ProphecyDAO {
    Prophecy[] getProphecies() throws IOException;

    Prophecy[] findProphecies(String name) throws IOException;

    Prophecy getProphecy(int id) throws IOException;

    Prophecy createProphecy(Prophecy prophecy) throws IOException;

    Prophecy updateProphecy(Prophecy prophecy) throws IOException;

    boolean deleteProphecy(int id) throws IOException;
    
}
