package com.school.repository;

import com.school.domain.Group;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Group entity.
 */
@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    @Query(value = "select distinct group from Group group left join fetch group.subjects left join fetch group.students",
        countQuery = "select count(distinct group) from Group group")
    Page<Group> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct group from Group group left join fetch group.subjects left join fetch group.students")
    List<Group> findAllWithEagerRelationships();

    @Query("select group from Group group left join fetch group.subjects left join fetch group.students where group.id =:id")
    Optional<Group> findOneWithEagerRelationships(@Param("id") Long id);
}
