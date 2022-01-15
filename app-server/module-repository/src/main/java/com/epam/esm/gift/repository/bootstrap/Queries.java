package com.epam.esm.gift.repository.bootstrap;

public record Queries(
    String selectAll,
    String selectOne,
    String deleteOne,
    String insertOne,
    String updateOne
) {
}
