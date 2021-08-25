package com.company.hit.dao;

import com.company.hit.dm.DataModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * implement IDao interface in json file
 * filePath : path of json file
 * capacity : max capacity of file
 * buffer : map that stores the memory while running
 *
 * @param <T> : type of data model content
 */
public class DaoFileImpl<T>
        extends Object
        implements IDao<Long, DataModel<T>> {

    private String filePath;
    private int capacity;
    private List<DataModel<T>> buffer;

    /**
     * reloads the buffer according to the Json file
     *
     * @param filePath : path of json file
     */
    private void loadData(String filePath) {

        Gson gson = new Gson();
        try {
            Type listType = new TypeToken<ArrayList<DataModel<T>>>() {
            }.getType();
            FileReader fileReader = new FileReader(filePath);
            ArrayList<DataModel<T>> fileArray = gson.fromJson(fileReader, listType);
            if (fileArray != null) {
                buffer = fileArray;
            }
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * update memory by buffer
     *
     * @param filePath : json file path
     */
    private void insertData(String filePath, List<DataModel<T>> buffer) {
        Gson gson = new Gson();
        try {
            Type listType = new TypeToken<ArrayList<DataModel<T>>>() {
            }.getType();
            FileWriter fileWriter = new FileWriter(filePath);
            gson.toJson(buffer, fileWriter);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * constructor - initialize variables and load data to buffer
     */
    public DaoFileImpl(String filePath) {
        this.filePath = filePath;
        capacity = 100;
        loadData(filePath);
    }

    /**
     * constructor
     */
    public DaoFileImpl(String filePath, int capacity) {
        this.filePath = filePath;
        this.capacity = capacity;
        loadData(filePath);
    }

    @Override
    public void save(DataModel<T> t) {
        loadData(filePath);
        for (int i = 0; i < buffer.size(); i++) {
            if (buffer.get(i).getDataModelId().equals(t.getDataModelId())) buffer.remove(i);
        }
        buffer.add(t);
        if (buffer.size() > capacity) {
            System.out.println("cannot save " + t.toString() + ": memory is full. delete data to make room");
            buffer.remove(buffer.size() - 1);
            return;
        }
        insertData(filePath, buffer);
    }

    @Override
    public void delete(DataModel<T> t) throws IllegalArgumentException {

        if (null == t) {
            throw new IllegalArgumentException("t is null");
        }
        loadData(filePath);
        buffer.remove(t);
        insertData(filePath, buffer);
    }

    @Override
    public DataModel<T> find(Long id) throws IllegalArgumentException {

        if (null == id) {
            throw new IllegalArgumentException("aLong is null");
        }
        loadData(filePath);
        for (DataModel<T> dm : buffer) {
            if (dm.getDataModelId().equals(String.valueOf(id))) return dm;
        }
        return null;
    }
}
