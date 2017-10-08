package com.ionkin.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by mikhail on 10/4/17.
 */
@XmlRootElement
@Entity
public class StudentTask implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;
    private Integer studentId;
    private Integer taskId;

    public StudentTask() {
    }

    public StudentTask(Integer studentId, Integer taskId) {
        this.studentId = studentId;
        this.taskId = taskId;
    }

    public StudentTask(Integer id, Integer studentId, Integer taskId) {
        this(studentId, taskId);
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String toString() {
        return String.format("StudentTask [id=%d, studentId=%d, taskId=%d]", id, studentId, taskId);
    }
}
