package com.epam.esm.gift.repository.bootstrap;

public class Queries {
    private final String selectAll;
    private final String selectOne;
    private final String deleteOne;
    private final String insertOne;
    private final String updateOne;

    public Queries(String selectAll, String selectOne, String deleteOne, String insertOne, String updateOne) {
        this.selectAll = selectAll;
        this.selectOne = selectOne;
        this.deleteOne = deleteOne;
        this.insertOne = insertOne;
        this.updateOne = updateOne;
    }

    public String selectAll() {
        return selectAll;
    }

    public String selectOne() {
        return selectOne;
    }

    public String deleteOne() {
        return deleteOne;
    }

    public String insertOne() {
        return insertOne;
    }

    public String updateOne() {
        return updateOne;
    }
}
