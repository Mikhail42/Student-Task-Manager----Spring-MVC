package com.ionkin.beans;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by mikhail on 10/4/17.
 */

@Entity
@XmlRootElement
public class Task implements Serializable {
    @Id
    @GeneratedValue
    private Integer id;

    public Task() {
    }

    public Task(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String toString() {
        return String.format("Task [id=%d]", id);
    }
}
