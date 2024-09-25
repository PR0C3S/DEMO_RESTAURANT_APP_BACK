package com.dev.app_restaurant.util;

import org.springframework.data.domain.Sort;

public class SortBy {
    private static SortBy instance = null;

    private SortBy(){}

    public static SortBy  getInstance(){
        if(instance==null){
            instance = new SortBy();
        }

        return instance;
    }

    public Sort getSort(String orderBy){
        String[] filters = orderBy.split("-");
        String fieldOrderBy = filters[0];
        String typeOrderBy = filters[1];
        Sort sort = Sort.by(fieldOrderBy).descending();

        if (typeOrderBy.equalsIgnoreCase("ASC")) {
            sort = Sort.by(fieldOrderBy).ascending();
        }
        return sort;
    }


}
