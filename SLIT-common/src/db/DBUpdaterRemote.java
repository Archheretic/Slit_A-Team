/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import javax.ejb.Remote;
import javax.swing.JTextArea;
import slitcommon.DeliveryStatus;

/**
 * @author Viktor Setervang
 * @author Arild Høyland
 */
@Remote
public interface DBUpdaterRemote {
    
    public Connection dbConnection();
    
    public String addDeliveryEvaluation(String evaluationValue, String evaluatedByValue, 
        int whereValue1, String whereValue2, DeliveryStatus evaluationStatus); 
    
    public void markNotificationsAsSeen(ArrayList<Integer> idNotification);

    public String updateModul(ArrayList<String> listOfEdits, int idModul);
}
