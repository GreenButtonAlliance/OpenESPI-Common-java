package org.energyos.espi.common.domain;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

@Entity
@Table(name = "data_custodians")
@NamedQueries(value = {
        @NamedQuery(name = DataCustodian.QUERY_FIND_ALL,
                query = "SELECT custodian FROM DataCustodian custodian"),
        @NamedQuery(name = DataCustodian.QUERY_FIND_BY_ID,
            query = "SELECT custodian FROM DataCustodian custodian WHERE custodian.id = :id"),
        @NamedQuery(name = DataCustodian.QUERY_FIND_BY_CLIENT_ID,
            query = "SELECT custodian FROM DataCustodian custodian WHERE custodian.clientId = :clientId")
})
public class DataCustodian {
    public static final String QUERY_FIND_ALL = "DataCustodian.findAll";
    public static final String QUERY_FIND_BY_ID = "DataCustodian.findById";
    public static final String QUERY_FIND_BY_CLIENT_ID = "DataCustodian.findByClientId";

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String description;

    @Column(name = "client_id")
    @NotEmpty
    private String clientId;

    @NotEmpty
    private String url;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getScopeSelectionURL() {
        return getUrl() + Routes.DATA_CUSTODIAN_SCOPE_SELECTION_SCREEN;
    }

}
