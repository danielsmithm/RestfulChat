package br.ufrn.gui;

import br.ufrn.chat.ChatAPI;
import br.ufrn.chat.messagery.MessageHandler;
import br.ufrn.chat.exception.ChatCommunicationException;
import br.ufrn.chat.factory.ChatAPIFactory;
import br.ufrn.dto.GroupDTO;
import br.ufrn.dto.MessageDTO;
import br.ufrn.dto.UserDTO;
import br.ufrn.exceptions.UserNotFoundException;
import br.ufrn.utils.ExceptionHandlingUtils;
import br.ufrn.exceptions.GroupNotExistsException;
import br.ufrn.exceptions.UserAlreadyExistsException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Scanner;

/**
 * Implementação da GUI do chat.
 */
public class ChatGui extends JFrame{

    private GroupDTO group;
    private final ChatAPI chatAPI;
    private final UserDTO activeUser;

    private JPanel chatPannel;
    private JTextArea messageTextArea;
    private JButton sendMessageButton;
    private JList<String> messageList;

    public static void main(String[] args) {

        try {
            ChatAPI chatAPI = ChatAPIFactory.getChatApi();

            UserDTO activeUser = registerUser(chatAPI);

            GroupChooseGui.openGroupChooseGui(chatAPI, activeUser);
        } catch (ChatCommunicationException e) {
            JOptionPane.showMessageDialog(null,"Wasn't possible estabilish a connection to server. Try again later.");
        }

    }

    public ChatGui(UserDTO activeUser, GroupDTO group, ChatAPI chatAPI){
        this.activeUser = activeUser;
        this.chatAPI = chatAPI;
        this.group = group;
        initGui();
    }

    private void initGui() {
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 480);
        setPreferredSize(getSize());
        setTitle("Restful");

        JPanel wrapperPannel = new JPanel();
        wrapperPannel.setPreferredSize(getSize());

        JButton quitChatButton = new JButton();
        quitChatButton.setText("Quit chat");
        quitChatButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    chatAPI.quitGroup(group.getId(),activeUser);
                    setVisible(false);
                    GroupChooseGui.openGroupChooseGui(chatAPI,activeUser);
                } catch (ChatCommunicationException e1) {
                    ExceptionHandlingUtils.handleChatCommunitationException(e1);
                } catch (GroupNotExistsException e1) {
                    ExceptionHandlingUtils.handleGroupNotExistsException(e1);
                } catch (UserNotFoundException e1) {
                    ExceptionHandlingUtils.handleUserNotFoundException(e1);
                }

            }
        });

        wrapperPannel.add(quitChatButton);

        this.chatPannel = createChatPannel();

        wrapperPannel.add(this.chatPannel);
        wrapperPannel.add(createMessageSenderPannel());

        add(wrapperPannel);

        try {
            registerMessageHandler();
        } catch (ChatCommunicationException e) {
            ExceptionHandlingUtils.handleChatCommunitationException(e);
        }

        pack();
        setVisible(true);
    }

    private JPanel createMessageSenderPannel() {
        JPanel messageSendPanel = new JPanel();

        messageSendPanel.setSize(800,140);
        messageSendPanel.setPreferredSize(messageSendPanel.getSize());

        this.messageTextArea = new JTextArea();

        this.messageTextArea.setSize(480,140);
        this.messageTextArea.setPreferredSize(this.messageTextArea.getSize());

        this.messageTextArea.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    try {
                        chatAPI.sendMessageToGroup(group.getId(),activeUser.getUserName(),messageTextArea.getText());
                        messageTextArea.setText("");
                    } catch (ChatCommunicationException e1) {
                        ExceptionHandlingUtils.handleChatCommunitationException(e1);
                    } catch (GroupNotExistsException e1) {
                        ExceptionHandlingUtils.handleGroupNotExistsException(e1);
                    }
                }
            }
        });

        messageSendPanel.add(this.messageTextArea);

        return messageSendPanel;
    }

    private JPanel createChatPannel() {
        JPanel chatPannel = new JPanel();

        chatPannel.setSize(600,320);
        chatPannel.setPreferredSize(chatPannel.getSize());

        this.messageList = new JList<String>();
        this.messageList.setPreferredSize(chatPannel.getPreferredSize());
        this.messageList.setModel(new DefaultListModel<>());

        JScrollPane scrollPane = new JScrollPane(new JScrollPane(this.messageList));
        scrollPane.setPreferredSize(messageList.getPreferredSize());

        chatPannel.add(scrollPane);

        return chatPannel;
    }

    private static UserDTO registerUser(ChatAPI chatAPI) throws ChatCommunicationException {
        UserDTO activeUser;

        Scanner scanner = new Scanner(System.in);
        try{
            String usernameForRegistry = JOptionPane.showInputDialog("Digite um nome de usuário para registro.");

            activeUser = chatAPI.register(usernameForRegistry);

            return activeUser;
        } catch (UserAlreadyExistsException e) {
            ExceptionHandlingUtils.handleException(e);
        } catch (ChatCommunicationException e) {
            ExceptionHandlingUtils.handleChatCommunitationException(e);
        }

        throw new RuntimeException();
    }

    private void registerMessageHandler() throws ChatCommunicationException {

        final String userName = activeUser.getUserName();

        MessageHandler handler = new ChatGui.GUIMessageHandler();

        chatAPI.registerMessageHandler(handler);
    }

    /**
     * Message handler específico para interface de usuário.
     */
    private class GUIMessageHandler implements MessageHandler{

        protected GUIMessageHandler() {
            super();
        }

        @Override
        public String getUserName() {
            return activeUser.getUserName();
        }

        @Override
        public void notifyMessage(MessageDTO message){
            DefaultListModel<String> model = (DefaultListModel<String>) messageList.getModel();

            if(message.getGroupId().equals(group.getId())) {
                String formatedMessage = null;
                String dateInHoursMinutesFormat = new SimpleDateFormat("HH:mm").format(message.getSendTime());
                if (isServerMesssage(message)) {
                    formatedMessage = String.format("(%s) %s", dateInHoursMinutesFormat, message.getContent());
                } else {
                    formatedMessage = String.format("(%s) %s - %s", dateInHoursMinutesFormat, message.getAuthorUserName(), message.getContent());
                }

                model.addElement(formatedMessage);
            }

        }

        private boolean isServerMesssage(MessageDTO message){
            return message.getAuthorUserName().equals("server");
        }
    }

}
