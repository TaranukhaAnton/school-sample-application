package com.school.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A Student.
 */
@Entity
@Table(name = "student")
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @ManyToOne
    @JsonIgnoreProperties("students")
    private GradeLevel gradeLevel;

    @ManyToOne
    @JsonIgnoreProperties("students")
    private SchollClass schoolClass;

    @ManyToMany
    @JoinTable(name = "student_groups",
               joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "groups_id", referencedColumnName = "id"))
    private Set<Group> groups = new HashSet<>();

    @ManyToMany(mappedBy = "students")
    @JsonIgnore
    private Set<Group> groups = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public Student firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Student lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public GradeLevel getGradeLevel() {
        return gradeLevel;
    }

    public Student gradeLevel(GradeLevel gradeLevel) {
        this.gradeLevel = gradeLevel;
        return this;
    }

    public void setGradeLevel(GradeLevel gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    public SchollClass getSchoolClass() {
        return schoolClass;
    }

    public Student schoolClass(SchollClass schollClass) {
        this.schoolClass = schollClass;
        return this;
    }

    public void setSchoolClass(SchollClass schollClass) {
        this.schoolClass = schollClass;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public Student groups(Set<Group> groups) {
        this.groups = groups;
        return this;
    }

    public Student addGroups(Group group) {
        this.groups.add(group);
        group.getStudents().add(this);
        return this;
    }

    public Student removeGroups(Group group) {
        this.groups.remove(group);
        group.getStudents().remove(this);
        return this;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public Student groups(Set<Group> groups) {
        this.groups = groups;
        return this;
    }

    public Student addGroups(Group group) {
        this.groups.add(group);
        group.getStudents().add(this);
        return this;
    }

    public Student removeGroups(Group group) {
        this.groups.remove(group);
        group.getStudents().remove(this);
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
        if (!(o instanceof Student)) {
            return false;
        }
        return id != null && id.equals(((Student) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Student{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            "}";
    }
}
