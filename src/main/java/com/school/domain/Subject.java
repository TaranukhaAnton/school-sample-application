package com.school.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A Subject.
 */
@Entity
@Table(name = "subject")
public class Subject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "common")
    private Boolean common;

    @ManyToMany
    @JoinTable(name = "subject_grade_levels",
               joinColumns = @JoinColumn(name = "subject_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "grade_levels_id", referencedColumnName = "id"))
    private Set<GradeLevel> gradeLevels = new HashSet<>();

    @ManyToMany(mappedBy = "subjects")
    @JsonIgnore
    private Set<Group> groups = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Subject name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isCommon() {
        return common;
    }

    public Subject common(Boolean common) {
        this.common = common;
        return this;
    }

    public void setCommon(Boolean common) {
        this.common = common;
    }

    public Set<GradeLevel> getGradeLevels() {
        return gradeLevels;
    }

    public Subject gradeLevels(Set<GradeLevel> gradeLevels) {
        this.gradeLevels = gradeLevels;
        return this;
    }

    public Subject addGradeLevels(GradeLevel gradeLevel) {
        this.gradeLevels.add(gradeLevel);
        gradeLevel.getSubjects().add(this);
        return this;
    }

    public Subject removeGradeLevels(GradeLevel gradeLevel) {
        this.gradeLevels.remove(gradeLevel);
        gradeLevel.getSubjects().remove(this);
        return this;
    }

    public void setGradeLevels(Set<GradeLevel> gradeLevels) {
        this.gradeLevels = gradeLevels;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public Subject groups(Set<Group> groups) {
        this.groups = groups;
        return this;
    }

    public Subject addGroups(Group group) {
        this.groups.add(group);
        group.getSubjects().add(this);
        return this;
    }

    public Subject removeGroups(Group group) {
        this.groups.remove(group);
        group.getSubjects().remove(this);
        return this;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Subject)) {
            return false;
        }
        return id != null && id.equals(((Subject) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Subject{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", common='" + isCommon() + "'" +
            "}";
    }
}
