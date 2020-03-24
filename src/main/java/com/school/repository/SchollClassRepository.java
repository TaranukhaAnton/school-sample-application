package com.school.repository;

import com.school.domain.SchollClass;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the SchollClass entity.
 */
@Repository
public interface SchollClassRepository extends JpaRepository<SchollClass, Long> {

    @Query(value = "select distinct schollClass from SchollClass schollClass left join fetch schollClass.gradeLevels",
        countQuery = "select count(distinct schollClass) from SchollClass schollClass")
    Page<SchollClass> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct schollClass from SchollClass schollClass left join fetch schollClass.gradeLevels")
    List<SchollClass> findAllWithEagerRelationships();

    @Query("select schollClass from SchollClass schollClass left join fetch schollClass.gradeLevels where schollClass.id =:id")
    Optional<SchollClass> findOneWithEagerRelationships(@Param("id") Long id);
}
