package cn.haiwai.newsnotification.dao.bean;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

@Entity
@Table(name = "role")
public class RoleDO implements Serializable {

    private static final long serialVersionUID = -7594818093525889580L;
    private Integer id;
    private String name;
    private Set<ModuleDO> modules;

    @Id
    @Column(name = "rid")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_module", joinColumns = @JoinColumn(name = "rid", referencedColumnName = "rid"), inverseJoinColumns = @JoinColumn(name = "mid", referencedColumnName = "mid"))
    public Set<ModuleDO> getModules() {
        return modules;
    }

    public void setModules(Set<ModuleDO> modules) {
        this.modules = modules;
    }

    @Transient
    public Set<String> getModulesName() {
        if (this.modules.isEmpty())
            return null;
        Set<String> strings = new TreeSet<>();
        for (ModuleDO moduleDO : this.modules) {
            strings.add(this.name+":"+moduleDO.getName());
        }
        return strings;
    }
}
