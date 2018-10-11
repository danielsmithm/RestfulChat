package br.ufrn.gui;

import br.ufrn.chat.ChatAPI;
import br.ufrn.chat.exception.ChatCommunicationException;
import br.ufrn.dto.GroupDTO;
import br.ufrn.dto.UserDTO;
import br.ufrn.exceptions.GroupNotExistsException;
import br.ufrn.exceptions.UserNotFoundException;
import br.ufrn.utils.ExceptionHandlingUtils;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * Implementação da GUI de escolha do grupo.
 */
public class GroupChooseGui extends JFrame {

    private final UserDTO activeUser;
    private final ChatAPI chatAPI;
    private List<GroupDTO> existentGroups;

    private JList<GroupDTO> messageList;
    private JButton groupChooseButton;

    public GroupChooseGui(ChatAPI chatAPI, final UserDTO activeUser) throws ChatCommunicationException {
        this.activeUser = activeUser;
        this.chatAPI = chatAPI;

        loadGroups(chatAPI, activeUser);

        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 480);
        setPreferredSize(getSize());
        setTitle("Restful");

        JPanel wrapperPannel = new JPanel();
        wrapperPannel.setPreferredSize(getSize());

        JButton createChatButton = new JButton();
        createChatButton.setText("Create group");
        createChatButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    String groupName = JOptionPane.showInputDialog("Enter a group name.");

                    if(groupName != null){
                        setVisible(false);
                        chatAPI.createGroup(groupName,activeUser);
                        GroupChooseGui.openGroupChooseGui(chatAPI,activeUser);
                    }else{
                        JOptionPane.showMessageDialog(null,"Insert a valid group name.");
                    }

                } catch (ChatCommunicationException e1) {
                    ExceptionHandlingUtils.handleChatCommunitationException(e1);
                } catch (UserNotFoundException e1) {
                    ExceptionHandlingUtils.handleUserNotFoundException(e1);
                }

            }
        });

        wrapperPannel.add(createChatButton);
        wrapperPannel.add(createGuiChoosePanel());
        wrapperPannel.add(createChooseGroupButtonPannel());

        add(wrapperPannel);
        pack();
        setVisible(true);
    }

    static void openGroupChooseGui(ChatAPI chatAPI, UserDTO activeUser) throws ChatCommunicationException {
        new GroupChooseGui(chatAPI,activeUser);
    }

    private void loadGroups(ChatAPI chatAPI, UserDTO activeUser) throws ChatCommunicationException {
        this.existentGroups = chatAPI.listGroups();
    }

    private Component createGuiChoosePanel() {

        JPanel guiChoosePanel = new JPanel();

        guiChoosePanel.setSize(600,320);
        guiChoosePanel.setPreferredSize(guiChoosePanel.getSize());

        this.messageList = new JList<GroupDTO>();
        this.messageList.setPreferredSize(guiChoosePanel.getPreferredSize());

        final DefaultListModel<GroupDTO> model = new DefaultListModel<>();

        existentGroups.forEach(group -> model.addElement(group));

        this.messageList.setModel(model);

        JScrollPane scrollPane = new JScrollPane(new JScrollPane(this.messageList));
        scrollPane.setPreferredSize(messageList.getPreferredSize());

        guiChoosePanel.add(scrollPane);

        groupChooseButton = new JButton();
        groupChooseButton.setText("Choose group");
        groupChooseButton.addActionListener(createGroupChooseHandler());

        guiChoosePanel.add(this.groupChooseButton);

        return guiChoosePanel;
    }

    private Component createChooseGroupButtonPannel() {

        JPanel guiChoosePanel = new JPanel();

        guiChoosePanel.setSize(600,320);
        guiChoosePanel.setPreferredSize(guiChoosePanel.getSize());

        groupChooseButton = new JButton();
        groupChooseButton.setText("Choose group");
        groupChooseButton.addActionListener(createGroupChooseHandler());

        guiChoosePanel.add(this.groupChooseButton);

        return guiChoosePanel;
    }

    private AbstractAction createGroupChooseHandler() {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int numberOfSelectedItemsOnList = messageList.getSelectedIndices().length;

                if(numberOfSelectedItemsOnList == 1){
                    int selectIndex = messageList.getSelectedIndices()[0];
                    GroupDTO group = messageList.getModel().getElementAt(selectIndex);

                    try {
                        chatAPI.joinGroup(group.getId(),activeUser);
                        setVisible(false);

                        new ChatGui(activeUser, group, chatAPI);
                    } catch (ChatCommunicationException e1) {
                        ExceptionHandlingUtils.handleChatCommunitationException(e1);
                    } catch (GroupNotExistsException e1) {
                        ExceptionHandlingUtils.handleGroupNotExistsException(e1);
                    } catch (UserNotFoundException e1) {
                        ExceptionHandlingUtils.handleUserNotFoundException(e1);
                    }

                }else if(numberOfSelectedItemsOnList == 0){
                    JOptionPane.showMessageDialog(null,"Select a group.");
                }else{
                    JOptionPane.showMessageDialog(null,"Select a single group.");
                }
            }
        };
    }

}
