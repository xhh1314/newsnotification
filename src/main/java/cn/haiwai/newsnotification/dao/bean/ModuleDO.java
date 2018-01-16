package cn.haiwai.newsnotification.dao.bean;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "module")
public class ModuleDO {
    private static final long serialVersionUID = -7594818093595889330L;
    private Integer id;
    private String name;

    @Id
    @Column(name="mid")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
