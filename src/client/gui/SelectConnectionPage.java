package client.gui;
import client.WriterInPage;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.*;

public class SelectConnectionPage extends javax.swing.JPanel {

    private javax.swing.JButton connectBtn;
    private javax.swing.JTable listTable;
    private javax.swing.JButton listBtn;
    private javax.swing.JScrollPane scrollPane;
    private JLabel lbl1 ;
    private String userName ;
    private int serverPort ;
    String serverIp ;
    WriterInPage writerInPage ;



    public void setClient1(String userName)
    {
        this.userName = userName;
    }

    public SelectConnectionPage(WriterInPage writerInPage ) {
        this.writerInPage =writerInPage;
        initComponents();
    }
    public void setPort(int port)
    {
        this.serverPort = port;
    }

    public void setIp(String ip)
    {
        this.serverIp = ip;
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        JScrollPane scrollPane = new javax.swing.JScrollPane();
        listTable = new javax.swing.JTable();
        listBtn = new javax.swing.JButton();
        connectBtn = new javax.swing.JButton();
        lbl1 = new JLabel("Online Hosts");

        lbl1.setBounds(27,35,137,20);
        lbl1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.add(lbl1);

        listTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {
                        {null},
                        {null}
                },
                new String [] {
                        "OnlineHosts"
                }
        ) {
            Class[] types = new Class [] {
                    java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                    false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scrollPane.setViewportView(listTable);

        listBtn.setText("Show Online Hosts");
        listBtn.addActionListener(evt -> listBtnActionPerformed(evt));

        connectBtn.setText("Connect to this host ...");
        connectBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                connectBtnActionPerformed(e);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(listTable, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 72, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(listBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(connectBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE))
                                .addGap(22, 22, 22))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(59, 59, 59)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(listTable, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(listBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(connectBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(97, Short.MAX_VALUE))
        );
    }

    private void listBtnActionPerformed(java.awt.event.ActionEvent evt) {

        DefaultTableModel dTM = new DefaultTableModel();
        System.out.println("list Btn pressed!");
        try
        {
            byte[] buffOut= "List".getBytes();
            byte[] buffIn = new byte[128];
            DatagramSocket socket = new DatagramSocket();
            System.out.println(InetAddress.getLocalHost());
            DatagramPacket packetout = new DatagramPacket(buffOut,buffOut.length, InetAddress.getLocalHost(), serverPort);
            DatagramPacket packetin = new DatagramPacket(buffIn,buffIn.length);
            socket.send(packetout);
            System.out.println("packet send");
            socket.receive(packetin);
            String string = new String(packetin.getData() , packetin.getOffset() , packetin.getLength());
            String [] data = string.split(" ");
            dTM.addColumn("OnlineHosts",data);
            listTable.setModel(dTM);
        } catch (SocketException e)
        {
            e.printStackTrace();
        } catch (UnknownHostException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    private void connectBtnActionPerformed(ActionEvent evt)
    {
        JFrame chat = new JFrame("Chat Page ");
        chat.setSize(500,500);
        chat.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        writerInPage.initComponents();
        chat.add(writerInPage);
        chat.setVisible(true);
        try
        {
            int row = listTable.getSelectedRow();
            int col = listTable.getSelectedColumn();
            String userName2 = (String)listTable.getValueAt(row,col);
            byte[] buff = ("Connect"+" "+ userName + " " + userName2).getBytes();
            System.out.println(userName + " " + userName2);
            DatagramSocket socket = new DatagramSocket();
            System.out.println(InetAddress.getByName(serverIp));
            System.out.println(InetAddress.getLocalHost());
            DatagramPacket packet = new DatagramPacket(buff , buff.length ,InetAddress.getByName(serverIp) ,serverPort );
            socket.send(packet);
        } catch (SocketException e)
        {
            System.err.println("socket exc in select page ...");
        } catch (UnknownHostException e)
        {
            System.err.println("UnknownHostException happened in select page..");
        } catch (IOException e)
        {
            System.err.println("IO exc in select page...");
        }

    }
}

