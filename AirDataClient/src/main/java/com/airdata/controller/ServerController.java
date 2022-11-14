package com.airdata.controller;

import com.airdata.dao.ExceptionDAO;
import com.airdata.model.Server;
import com.airdata.dao.ServerDAO;
import com.airdata.view.Dash;
import com.github.britooo.looca.api.core.Looca;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class ServerController {
    
    public Server getServer() throws SocketException, UnknownHostException, ExceptionDAO {
        
        String macAddress = getMacAddress();
        
        Server server = new Server(macAddress);
        ServerDAO serverDAO = new ServerDAO();
        
        List serverComponentList = (List) serverDAO.getServer(server);
        
        if(!serverComponentList.isEmpty()) {
            server.setIdCpu((Integer) serverComponentList.get(0));
            server.setIdDisk((Integer) serverComponentList.get(1));
            server.setIdRam((Integer) serverComponentList.get(2));
            server.setIdTemp((Integer) serverComponentList.get(3));
            return server;
        }
        
        return null;
    }
    
    public List saveData(Server server) throws ExceptionDAO, ClassNotFoundException, SocketException, UnknownHostException {
        
        Looca looca = new Looca();
        ServerDAO serverDAO = new ServerDAO();
        
        List dataList = new ArrayList();
        
        Double cpuPercent = looca.getProcessador().getUso();
        Long ramPercent = (looca.getMemoria().getEmUso() * 100) / looca.getMemoria().getTotal();
        Long freeDisk = looca.getGrupoDeDiscos().getVolumes().get(0).getDisponivel();
        Long totalDisk = looca.getGrupoDeDiscos().getVolumes().get(0).getTotal();
        Long diskPercent = (freeDisk * 100) / totalDisk;
        
        serverDAO.saveData(1, server.getIdCpu(), cpuPercent.intValue(), server.getMacAddress());
        serverDAO.saveData(2, server.getIdRam(), ramPercent.intValue(), server.getMacAddress());
        serverDAO.saveData(3, server.getIdDisk(), diskPercent.intValue(), server.getMacAddress());
        
        dataList.add(server.getMacAddress());
        dataList.add(cpuPercent.intValue());
        dataList.add(ramPercent.intValue());
        dataList.add(diskPercent.intValue());
        
        return dataList;
    }
    
    private String getMacAddress() throws SocketException, UnknownHostException {
        InetAddress addr = InetAddress.getLocalHost();
        NetworkInterface iface = NetworkInterface.getByInetAddress(addr);
  
        byte[] mac = iface.getHardwareAddress();
        
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mac.length; i++) {
            sb.append(String.format(
                "%02X%s", mac[i],
                (i < mac.length - 1) ? ":" : ""));
        }
        
        return sb.toString();
        
    }
    
}
