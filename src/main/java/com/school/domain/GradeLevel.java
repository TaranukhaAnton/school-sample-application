package com.school.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A GradeLevel.
 */
@Entity
@Table(name = "grade_level")
public class GradeLevel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "value")
    private Integer value;

    @ManyToMany(mappedBy = "gradeLevels")
    @JsonIgnore
    private Set<SchollClass> schoolClasses = new HashSet<>();

    @ManyToMany(mappedBy = "gradeLevels")
    @JsonIgnore
    private Set<Subject> subjects = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getValue() {
        return value;
    }

    public GradeLevel value(Integer value) {
        this.value = value;
        return this;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Set<SchollClass> getSchoolClasses() {
        return schoolClasses;
    }

    public GradeLevel schoolClasses(Set<SchollClass> schollClasses) {
        this.schoolClasses = schollClasses;
        return this;
    }

    public GradeLevel addSchoolClasses(SchollClass schollClass) {
        this.schoolClasses.add(schollClass);
        schollClass.getGradeLevels().add(this);
        return this;
    }

    public GradeLevel removeSchoolClasses(SchollClass schollClass) {
        this.schoolClasses.remove(schollClass);
        schollClass.getGradeLevels().remove(this);
        return this;
    }

    public void setSchoolClasses(Set<SchollClass> schollClasses) {
        this.schoolClasses = schollClasses;
    }

    public Set<Subject> getSubjects() {
        return subjects;
    }

    public GradeLevel subjects(Set<Subject> subjects) {
        this.subjects = subjects;
        return this;
    }

    public GradeLevel addSubjects(Subject subject) {
        this.subjects.add(subject);
        subject.getGradeLevels().add(this);
        return this;
    }

    public GradeLevel removeSubjects(Subject subject) {
        this.subjects.remove(subject);
        subject.getGradeLevels().remove(this);
        return this;
    }

    public void setSubjects(Set<Subject> subjects) {
        this.subjects = subjects;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GradeLevel)) {
            return false;
        }
        return id != null && id.equals(((GradeLevel) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "GradeLevel{" +
            "id=" + getId() +
            ", value=" + getValue() +
            "}";
    }
}
