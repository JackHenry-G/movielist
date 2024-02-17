package com.goggin.movielist.model;

import java.util.List;

import lombok.ToString;

@ToString
public class TmdbResponse {
    private int page;
    private List<TmdbResponseResult> results;
    private int total_pages;
    private int total_results;

    public TmdbResponse() {

    }

    public TmdbResponse(int page, List<TmdbResponseResult> results, int total_pages, int total_results) {
        this.page = page;
        this.results = results;
        this.total_pages = total_pages;
        this.total_results = total_results;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<TmdbResponseResult> getResults() {
        return results;
    }

    public void setResults(List<TmdbResponseResult> results) {
        this.results = results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

}
