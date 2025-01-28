package org.szymonmielecki.ordersmanager.order;


public class OrderUpdateDTO {
    private String status;
    private String client_id;

    public OrderUpdateDTO(String status, String client_id) {
        this.status = status;
        this.client_id = client_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

}
