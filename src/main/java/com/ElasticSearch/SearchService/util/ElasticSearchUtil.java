package com.ElasticSearch.SearchService.util;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;

import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;


import lombok.val;

import co.elastic.clients.elasticsearch._types.query_dsl.*;
import lombok.val;

import java.util.function.Supplier;

public class ElasticSearchUtil
{
    public static Supplier<Query> supplier() {
        Supplier<Query> supplier = () -> Query.of(q -> q.matchAll(matchAllQuery()));
        return supplier;
    }

    public static MatchAllQuery matchAllQuery() {
        MatchAllQuery.Builder matchAllQuery = new MatchAllQuery.Builder();
        return matchAllQuery.build();
    }

    public static Supplier<Query> supplierWithNameField(String fieldValue) {
        Supplier<Query> supplier = () -> Query.of(q -> q.match(matchQueryWithNameField(fieldValue)));
        return supplier;
    }

    public static MatchQuery matchQueryWithNameField(String fieldValue) {
        MatchQuery.Builder matchQuery = new MatchQuery.Builder();
        return matchQuery.field("name").query(fieldValue).build();
    }


public static Supplier<Query> createSupplierAutoSuggest(String partialProductName) {
    return () -> Query.of(q -> q.wildcard(w -> w
            .field("name")
            .value(partialProductName + "*")  // Wildcard for auto-suggestions
            .caseInsensitive(true)));
}

    public static MatchQuery createAutoSuggestMatchQuery(String partialProductName) {
        MatchQuery.Builder autoSuggestQuery = new MatchQuery.Builder();
        return autoSuggestQuery.field("name").query(partialProductName).analyzer("edge_ngram_analyzer").build();

    }

    public static Supplier<Query> fuzzySearchSupplier(String fieldValue) {
        Supplier<Query> supplier = () -> Query.of(q -> q.fuzzy(fuzzyQuery(fieldValue)));
        return supplier;
    }

    public static FuzzyQuery fuzzyQuery(String fieldValue) {
        FuzzyQuery.Builder fuzzyQuery = new FuzzyQuery.Builder();
        // You can specify the fuzziness, for example, "AUTO" or a numeric value
        return fuzzyQuery.field("name").value(fieldValue).fuzziness("AUTO").build();
    }

  //  Bool Query

    public static Supplier<Query> buildCustomSearchQuery(String nameQuery, String descriptionQuery, String prefix, String category) {
        return () -> {
            BoolQuery.Builder boolQuery = new BoolQuery.Builder();

            // Primary: Fuzzy search on "name" (mandatory match)
            if (nameQuery != null && !nameQuery.isEmpty()) {
                boolQuery.must(s -> s.match(m -> m
                        .field("name")
                        .query(nameQuery)
                        .fuzziness("AUTO")));
            }

            // Secondary: Fuzzy match on "description" (optional match)
            if (descriptionQuery != null && !descriptionQuery.isEmpty()) {
                boolQuery.should(s -> s.match(m -> m
                        .field("description")
                        .query(descriptionQuery)
                        .fuzziness("AUTO")));
            }

            // Prefix match for "name" (optional for auto-suggestions)
            if (prefix != null && !prefix.isEmpty()) {
                boolQuery.should(s -> s.prefix(p -> p
                        .field("name")
                        .value(prefix)));
            }

            // Filter by "category" if provided
            if (category != null && !category.isEmpty()) {
                boolQuery.filter(f -> f.term(t -> t
                        .field("category")
                        .value(category)));
            }

            return Query.of(q -> q.bool(boolQuery.build()));
        };
    }


    public static SortOptions sortByRatingDesc() {
        return SortOptions.of(s -> s
                .field(f -> f
                        .field("rating")
                        .order(SortOrder.Desc)));
    }








}




