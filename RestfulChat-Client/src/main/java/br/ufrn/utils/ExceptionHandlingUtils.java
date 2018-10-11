package br.ufrn.utils;

import br.ufrn.chat.exception.ChatCommunicationException;
import br.ufrn.exceptions.GroupNotExistsException;
import br.ufrn.exceptions.UserNotFoundException;

import javax.swing.*;
import java.rmi.RemoteException;

public class ExceptionHandlingUtils {

    public static void handleGroupNotExistsException(GroupNotExistsException ex) {
        showMessageDialog("The corresponding group to this chat message was not found. It may be deleted. ");
    }

    public static void handleChatCommunitationException(ChatCommunicationException ce) {
        showMessageDialog("An communication error ocurred while send the message to server. Try again later.");
    }

    public static void showMessageDialog(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    public static void handleUserNotFoundException(UserNotFoundException e1) {
        showMessageDialog(e1.getMessage());
    }

    public static void handleException(Exception e1) {
        showMessageDialog(e1.getMessage());
    }
}
