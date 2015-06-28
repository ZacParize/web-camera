package com.mycompany.server;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class Sender extends Thread {

    private String resource;
    private Socket socket;

    public String getResource() {
        return resource;
    }

    private void setResource(String resource) {
        this.resource = resource;
    }

    public Socket getSocket() {
        return socket;
    }

    private void setSocket(Socket socket) {
        this.socket = socket;
    }

    public Sender(Socket socket,String resource,Integer timeout) {
        this.setSocket(socket);
        try {
            this.getSocket().setSoTimeout(timeout);
            this.setResource(resource);
            this.start();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        if (this.getSocket() != null && this.getSocket().isConnected())
            try {
                /*if (this.getSocket().getInputStream() != null && this.getSocket().getInputStream().available() == 0) this.getSocket().getInputStream().close();
                if (this.getSocket().getOutputStream() != null) this.getSocket().getOutputStream().close();*/
                this.getSocket().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    private void send() {
        if (this.getResource() != null && this.getSocket() != null) {
            try {
                if (this.getSocket().isConnected()) {
                    // и оттуда же - поток данных от сервера к клиенту
                    OutputStream outputStream = this.getSocket().getOutputStream();
                    // буффер данных в 64 килобайта
                    File file = new File(this.getResource());
                    if (file.exists() && file.isFile()) {
                        try (FileInputStream fileInputStream = new FileInputStream(file)) {
                            int content;
                            while ((content = fileInputStream.read()) != -1) {
                                outputStream.write(content);
                                //convert to char and display it
                                //System.out.print((char) content);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        outputStream.write(-1);
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {

            }
        }
    }

    private void receive() {
        if (this.getResource() != null && this.getSocket() != null) {
            try {
                if (this.getSocket().isConnected()) {
                    // из сокета клиента берём поток входящих данных
                    InputStream inputStream = this.getSocket().getInputStream();
                    File file = new File(this.getResource());
                    if (file.exists() && file.isFile()) {
                        FileOutputStream fileOutputStream = new FileOutputStream(file);
                        try {
                            int content;
                            while ((content = inputStream.read()) != -1) {
                                fileOutputStream.write(content);
                                // convert to char and display it
                                //System.out.print((char)content);
                            }
                        } catch (IOException e) {

                        } finally {
                            try {
                                fileOutputStream.flush();
                                fileOutputStream.close();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {

            }
        }
    }

    @Override
    public void run() {
        //this.receive();
        this.send();
        this.close();
        // если файл существует и является директорией,
        // то ищем индексный файл index.html
        /*File f = new File(this.getResource());
        /*boolean flag = !f.exists();
        if(!flag) if(f.isDirectory())
        {
            if(path.lastIndexOf(""+File.separator) == path.length()-1)
                path = path + "index.html";
            else
                path = path + File.separator + "index.html";
            f = new File(path);
            flag = !f.exists();
        }*/

        // если по указанному пути файл не найден
        // то выводим ошибку "404 Not Found"
        /*if(f.exists() && f.isDirectory())
        {
            // первая строка ответа
            String response = "HTTP/1.1 404 Not Found\n";

            // дата в GMT
            DateFormat df = DateFormat.getTimeInstance();
            df.setTimeZone(TimeZone.getTimeZone("GMT"));
            response = response + "Date: " + df.format(new Date()) + "\n";

            // остальные заголовки
            response = response
                    + "Content-Type: text/plain\n"
                    + "Connection: close\n"
                    + "Server: SimpleWEBServer\n"
                    + "Pragma: no-cache\n\n";

            // и гневное сообщение
            response = response + "File " + this.getResource() + " not found!";

            // выводим данные:
            os.write(response.getBytes());

            // завершаем соединение
            this.getSocket().close();

            // выход
            return;
        }

        // определяем MIME файла по расширению
        // MIME по умолчанию - "text/plain"
        /*String mime = "text/plain";

        // выделяем у файла расширение (по точке)
        r = this.getResource().lastIndexOf(".");
        if(r > 0)
        {
            String ext = this.getResource().substring(r);
            if(ext.equalsIgnoreCase("html"))
                mime = "text/html";
            else if(ext.equalsIgnoreCase("htm"))
                mime = "text/html";
            else if(ext.equalsIgnoreCase("gif"))
                mime = "image/gif";
            else if(ext.equalsIgnoreCase("jpg"))
                mime = "image/jpeg";
            else if(ext.equalsIgnoreCase("jpeg"))
                mime = "image/jpeg";
            else if(ext.equalsIgnoreCase("bmp"))
                mime = "image/x-xbitmap";
        }

        // создаём ответ

        // первая строка ответа
        String response = "HTTP/1.1 200 OK\n";

        // дата создания в GMT
        DateFormat df = DateFormat.getTimeInstance();
        df.setTimeZone(TimeZone.getTimeZone("GMT"));

        // время последней модификации файла в GMT
        response = response + "Last-Modified: " + df.format(new Date(f.lastModified())) + "\n";

        // длина файла
        response = response + "Content-Length: " + f.length() + "\n";

        // строка с MIME кодировкой
        response = response + "Content-Type: " + mime + "\n";

        // остальные заголовки
        response = response
                + "Connection: close\n"
                + "Server: GlobalListener\n\n";

        // выводим заголовок:
        os.write(response.getBytes());*/

        // и сам файл:


                                /*
                                byte[] buf = fileInputStream.
                                // читаем 64кб от клиента, результат - кол-во реально принятых данных
                                int r = inputStream.read(buf);
                                // создаём строку, содержащую полученую от клиента информацию
                                String request = new String(buf, 0, r);
                                FileInputStream fis = new FileInputStream(this.getResource());
                                r = 1;
                                while (r > 0)
                                {
                                    r = fis.read(buf);
                                    if(r > 0) outputStream.write(buf, 0, r);
                                }*/

    }

}
