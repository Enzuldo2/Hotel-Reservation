package br.ufscar.dc.pooa.Model.interfaces;

import java.util.List;

public interface Service {
    int createService();
    Service getService();
    boolean updateService();
    boolean deleteService();
    List<Service> getServices();
    List<Service> getAvailableServices();
}