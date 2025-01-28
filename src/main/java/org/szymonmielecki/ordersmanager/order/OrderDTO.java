package org.szymonmielecki.ordersmanager.order;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class OrderDTO {
    private String id;

    private String status;
    private String clientId;
    private String creationDate;
    private List<String> productNames;


    @JsonCreator
    public OrderDTO(
            @JsonProperty("id") String id,
            @JsonProperty("status") String status,
            @JsonProperty("clientId") String clientId,
            @JsonProperty("creationDate") String creationDate,
            @JsonProperty("productNames") List<String> productNames
    ) {
        this.id = id;
        this.status = status;
        this.clientId = clientId;
        this.creationDate = creationDate;
        this.productNames = productNames;
    }


    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getProductNames() {
        return productNames;
    }

    public void setProductNames(List<String> productNames) {
        this.productNames = productNames;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
}
