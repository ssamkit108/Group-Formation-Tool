package com.dal.catmeclone.model;

public class Course {

    private int courseID;
    private String courseName;

    public Course() {
        super();
    }

    public Course(int courseID, String courseName) {
        super();
        this.courseID = courseID;
        this.courseName = courseName;
    }

    public Course(int courseID) {
        super();
        this.courseID = courseID;
    }

    public static boolean isCourseNameValid(String courseName) {
        if (courseName == null) {
            return false;
        }
        return !courseName.isEmpty();
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public boolean isCourseIDValid() {
        return (this.courseID != 0);
    }

    public boolean isCourseNameValid() {
        if (courseName != null && !courseName.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + courseID;
        result = prime * result + ((courseName == null) ? 0 : courseName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Course other = (Course) obj;
        if (courseID != other.courseID) {
            return false;
        }
        if (courseName == null) {
            if (other.courseName != null) {
                return false;
            }
        } else if (!courseName.equals(other.courseName)) {
            return false;
        }
        return true;
    }

}
