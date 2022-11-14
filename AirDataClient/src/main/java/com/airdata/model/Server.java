package com.airdata.model;

public class Server {
    
    private String macAddress;
    private Integer idCpu;
    private Integer idRam;
    private Integer idDisk;
    private Integer idTemp;

    public Server(String macAddress) {
        this.macAddress = macAddress;
        this.idCpu = null;
        this.idRam = null;
        this.idDisk = null;
        this.idTemp = null;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public Integer getIdCpu() {
        return idCpu;
    }

    public void setIdCpu(Integer idCpu) {
        this.idCpu = idCpu;
    }

    public Integer getIdRam() {
        return idRam;
    }

    public void setIdRam(Integer idRam) {
        this.idRam = idRam;
    }

    public Integer getIdDisk() {
        return idDisk;
    }

    public void setIdDisk(Integer idDisk) {
        this.idDisk = idDisk;
    }

    public Integer getIdTemp() {
        return idTemp;
    }

    public void setIdTemp(Integer idTemp) {
        this.idTemp = idTemp;
    }  
    
}
