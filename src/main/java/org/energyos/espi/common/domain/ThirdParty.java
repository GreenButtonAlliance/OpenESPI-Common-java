package org.energyos.espi.common.domain;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "third_parties", uniqueConstraints = {@UniqueConstraint(columnNames={"client_id"})})
@NamedQueries(value = {
        @NamedQuery(name = ThirdParty.QUERY_FIND_BY_ID, query = "SELECT thirdParty FROM ThirdParty thirdParty WHERE thirdParty.id = :id"),
        @NamedQuery(name = ThirdParty.QUERY_FIND_BY_CLIENT_ID, query = "SELECT thirdParty FROM ThirdParty thirdParty WHERE thirdParty.clientId = :clientId"),
        @NamedQuery(name = ThirdParty.QUERY_FIND_ALL, query = "SELECT thirdParty FROM ThirdParty thirdParty")
})
public class ThirdParty extends Resource {

    public final static String QUERY_FIND_ALL = "ThirdParty.findAll";
    public static final String QUERY_FIND_BY_ID = "ThirdParty.findById";
    public static final String QUERY_FIND_BY_CLIENT_ID = "ThirdParty.findByClientId";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "name")
    @NotEmpty @Size(min = 2, max = 64)
    protected String name;

    @Column(name = "url")
    protected String url;

    @Column(name = "client_id")
    @NotEmpty @Size(min = 2, max = 64)
    private String clientId;

    @Column(name = "notification_uri")
    private String notificationURI;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getNotificationURI() {
        return notificationURI;
    }

    public void setNotificationURI(String notificationURI) {
        this.notificationURI = notificationURI;
    }
}
