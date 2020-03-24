package com.school.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A SchollClass.
 */
@Entity
@Table(name = "scholl_class")
public class SchollClass implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany
    @JoinTable(name = "scholl_class_grade_levels",
               joinColumns = @JoinColumn(name = "scholl_class_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "grade_levels_id", referencedColumnName = "id"))
    private Set<GradeLevel> gradeLevels = new HashSet<>();

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

    public SchollClass name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<GradeLevel> getGradeLevels() {
        return gradeLevels;
    }

    public SchollClass gradeLevels(Set<GradeLevel> gradeLevels) {
        this.gradeLevels = gradeLevels;
        return this;
    }

    public SchollClass addGradeLevels(GradeLevel gradeLevel) {
        this.gradeLevels.add(gradeLevel);
        gradeLevel.getSchoolClasses().add(this);
        return this;
    }

    public SchollClass removeGradeLevels(GradeLevel gradeLevel) {
        this.gradeLevels.remove(gradeLevel);
        gradeLevel.getSchoolClasses().remove(this);
        return this;
    }

    public void setGradeLevels(Set<GradeLevel> gradeLevels) {
        this.gradeLevels = gradeLevels;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SchollClass)) {
            return false;
        }
        return id != null && id.equals(((SchollClass) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SchollClass{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
