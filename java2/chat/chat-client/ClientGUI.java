package ru.geekbrains.java2.chat.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;

public class ClientGUI extends JFrame  implements ActionListener, Thread.UncaughtExceptionHandler  {

    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;

    private final JTextArea log = new JTextArea();
    private final JPanel panelTop = new JPanel(new GridLayout(2, 3));
    private final JTextField tfIPAddress = new JTextField("127.0.0.1");
    private final JTextField tfPort = new JTextField("8189");
    private final JCheckBox cbAlwaysOnTop = new JCheckBox("Always on top");
    private final JTextField tfLogin = new JTextField("login");
    private final JPasswordField tfPassword = new JPasswordField("password");
    private final JButton btnLogin = new JButton("Login");

    private final JPanel panelBottom = new JPanel(new BorderLayout());
    private final JButton btnDisconnect = new JButton("<html><b>Disconnect</b></html>");
    private final JTextField tfMessage = new JTextField();
    private final JButton btnSend = new JButton("Send");

    private final JList<String> userList = new JList<>();


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClientGUI();
            }
        });
    }

    private ClientGUI() {
        Thread.setDefaultUncaughtExceptionHandler(this);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(WIDTH, HEIGHT);
        setTitle("Chat Client");
        log.setEditable(false);
        JScrollPane scrollLog = new JScrollPane(log);
        JScrollPane scrollUsers = new JScrollPane(userList);
        String[] users = {"user1", "user2", "user3", "user4", "user5", "User_with_a_very_long_name" };
        userList.setListData(users);
        scrollUsers.setPreferredSize(new Dimension(100, 0));
        // вдобавляем ActionListener на кнопку и сообщение
        btnSend.addActionListener(this);
        tfMessage.addActionListener(this);

        panelTop.add(tfIPAddress);
        panelTop.add(tfPort);
        panelTop.add(cbAlwaysOnTop);
        panelTop.add(tfLogin);
        panelTop.add(tfPassword);
        panelTop.add(btnLogin);
        panelBottom.add(btnDisconnect, BorderLayout.WEST);
        panelBottom.add(tfMessage, BorderLayout.CENTER);
        panelBottom.add(btnSend, BorderLayout.EAST);

        cbAlwaysOnTop.addActionListener(this);

        add(panelTop, BorderLayout.NORTH);
        add(panelBottom, BorderLayout.SOUTH);
        add(scrollLog, BorderLayout.CENTER);
        add(scrollUsers, BorderLayout.EAST);
        setVisible(true);
    }
    /* Домашнее задание курса java2 по уроку 4 "Продвинутые вопросы создания графического интерфейса"
       dated Jul 27, 2019

        Отправлять сообщения в лог по нажатию кнопки или по нажатию клавиши Enter.
        Создать лог в файле (показать комментарием, где и как Вы планируете писать сообщение в файловый журнал).
        Прочитать методичку к следующему уроку

    * */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == cbAlwaysOnTop) {
            setAlwaysOnTop(cbAlwaysOnTop.isSelected());
        }
        //отправлем сообщение
        else if (src == btnSend || src == tfMessage){
            sendMesage();
        }
        else {
            throw new RuntimeException("Unknown source: " + src);
        }
    }
    // метод отправляющий сообщение и записывающий лог в файл
    private void sendMesage(){
        String msg = tfMessage.getText() ;
        String username = tfLogin.getText();
        tfMessage.setText(null);
        tfMessage.grabFocus();
        putLog(String.format("%s: %s",username, msg));
        wrtMsgToLogFile(msg,username);
    }

    private void wrtMsgToLogFile(String msg, String username){

        try (FileWriter out = new FileWriter("chat.log", true)){
            out.write(username+": "+msg+"\n");
            out.flush();
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }
    private void putLog(String msg) {
        if("".equals(msg)) return;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                log.append(msg + "\n");
 //               log.setCaretPosition(log.getDocument().getLength());
            }
        });
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        e.printStackTrace();
        String msg;
        StackTraceElement[] ste = e.getStackTrace();
        if (ste.length == 0) {
            msg = "Empty Stacktrace";
        } else {
            msg = e.getClass().getCanonicalName() + ": " +
                    e.getMessage() + "\n\t at " + ste[0];
        }
        JOptionPane.showMessageDialog(this, msg, "Exception", JOptionPane.ERROR_MESSAGE);
        System.exit(1);
    }
}
