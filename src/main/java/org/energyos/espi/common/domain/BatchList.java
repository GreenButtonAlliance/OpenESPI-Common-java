package org.energyos.espi.common.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "BatchList")
public class BatchList {

    @XmlTransient
    private Long id;

    @XmlElement(name = "resource")
    private List<String> resources = new ArrayList<>();

    public List<String> getResources() {
        return resources;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
