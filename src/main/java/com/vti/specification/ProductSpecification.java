package com.vti.specification;

import com.vti.entity.Product;
import com.vti.form.ProductFilterForm;
import com.vti.utils.Utils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class ProductSpecification  {

    @SuppressWarnings("deprecation")
    public static Specification<Product> buildWhere(String search, ProductFilterForm filter) {

        Specification<Product> where = null;
        if (!StringUtils.isEmpty(search)) {
            search = Utils.formatSearch(search);
            CustomSpecification title = new CustomSpecification("title", search);
            where = Specification.where(title);
        }

        // filter by min Price
        if (filter != null && filter.getMinPrice() != null){
            CustomSpecification minPrice = new CustomSpecification("minPrice", filter.getMinPrice());
            if (where == null){
                where = minPrice;
            }else {
                where = where.and(minPrice);
            }
        }

        // filter by max Price
        if (filter != null && filter.getMaxPrice() != null){
            CustomSpecification maxPrice = new CustomSpecification("maxPrice", filter.getMaxPrice());
            if (where == null){
                where = maxPrice;
            }else {
                where = where.and(maxPrice);
            }
        }
        return where;
    }
}

@SuppressWarnings("serial")
@RequiredArgsConstructor
class CustomSpecification implements Specification<Product> {

    @NonNull
    private String field;
    @NonNull
    private Object value;

    @Override
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        if (field.equalsIgnoreCase("title")) {
            return criteriaBuilder.like(root.get("title"), "%" + value.toString() + "%");
        }
        if (field.equalsIgnoreCase("minPrice")){
            return  criteriaBuilder.greaterThanOrEqualTo(root.get("price"), value.toString());
        }
        if (field.equalsIgnoreCase("maxPrice")){
            return  criteriaBuilder.lessThanOrEqualTo(root.get("price"), value.toString());
        }
        return null;
    }

}
