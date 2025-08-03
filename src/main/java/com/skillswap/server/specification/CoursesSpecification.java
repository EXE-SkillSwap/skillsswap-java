package com.skillswap.server.specification;

import com.skillswap.server.entities.Courses;
import org.springframework.data.jpa.domain.Specification;

public class CoursesSpecification {

    public static Specification<Courses> hasSearchString(String searchString){
        return ((root, query, criteriaBuilder) -> {
            if(searchString == null || searchString.isEmpty()){
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.or(criteriaBuilder.like(criteriaBuilder.lower(root.get("courseName")), "%"+searchString.toLowerCase()+"%"),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), "%"+searchString.toLowerCase()+"%"));
        });
    }
}
