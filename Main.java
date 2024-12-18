import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main extends Frame implements Runnable, ActionListener {
    TextArea textArea;
    TextField textField;
    Button send;
    //ServerSocket serverSocket;
    Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    Thread chat;
    Main(){
        textField = new TextField();
        textArea = new TextArea();
        send = new Button("send");
        send.addActionListener(this);
        try {
            //serverSocket = new ServerSocket( 12000);
            socket = new Socket("localhost",12000);
            dataInputStream= new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
        }
        catch (Exception e){

        }
        add(textField);
        add(textArea);
        add(send);
        chat = new Thread(this);
        chat.setDaemon(true);
        chat.start();

        setSize(500,500);
        setLayout(new FlowLayout());
        //setShape(short);
        setTitle("ser");
        setVisible(true);

    }
    public void actionPerformed(ActionEvent e){
        String msg = textField.getText();
        textArea.append("ser:"+msg+"\n");
        textField.setText("");
        try {
            dataOutputStream.writeUTF(msg);
            dataOutputStream.flush();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }


    }

    public static void main(String[] args) {
        new Main();

    }
    public void run(){
        while(true){
            try{
                String msg = dataInputStream.readUTF();
                textArea.append("cli:"+msg+"\n");
            }
            catch(Exception e){

            }
        }
    }
}
