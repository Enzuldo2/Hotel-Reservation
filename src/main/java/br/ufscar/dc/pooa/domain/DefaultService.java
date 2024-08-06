package br.ufscar.dc.pooa.domain;

import java.util.List;

import br.ufscar.dc.pooa.interfaces.Service;

public class DefaultService implements Service {
    private String name;
    private int capacity;
    private String description;

    @Override
    public int createService() {
        // Implementação
        return 0;
    }

    @Override
    public Service getService() {
        // Implementação
        return null;
    }

    @Override
    public boolean updateService() {
        // Implementação
        return false;
    }

    @Override
    public boolean deleteService() {
        // Implementação
        return false;
    }

    @Override
    public List<Service> getServices() {
        // Implementação
        return null;
    }

    @Override
    public List<Service> getAvailableServices() {
        // Implementação
        return null;
    }

    // Getters e Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
