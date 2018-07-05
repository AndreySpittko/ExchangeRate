package com.company;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.X509Certificate;

public class Main {

    public static void main(String[] args) {
        disableSSL();
        try {
            String response = getLatesExchangeRates();
            if (response == null) {
                return;
            }


//            int c = 0;
//            char at = response.charAt(c);
//            System.out.println(at);

            for(int i = 0; i < response.length(); i++) {
                char c = response.charAt(i);

                System.out.print(c);
                if(c == '{'){
                    System.out.println("\t");
                }
                if(c ==':{'){

                }
                if(c == ',') {
                    System.out.print("\n\t");
                }
                if(c == '}') {
                    System.out.println();
                }

            }

            System.out.println(response);
            writeToFile(response);          //
        } catch (Exception e) {
            e.printStackTrace();
        }
        // write your code here
    }

    private static String getLatesExchangeRates() throws Exception {
        String url = "https://exchangeratesapi.io/api/latest";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            return response.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static void disableSSL() {
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }

            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        }};
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String readFromFile() {
        try {
            try (FileReader reader = new FileReader("test.txt")) {
                int c;
                while ((c = reader.read()) != -1) {
                    System.out.println((char) c);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private static void writeToFile(String textToWrite) {
//        String textToWrite = "Angey";
//        try(FileOutputStream fos = new FileOutputStream("test.txt");
//            BufferedOutputStream out = new BufferedOutputStream(fos)) {
            byte[] bytes = textToWrite.getBytes();
        try(FileWriter writer = new FileWriter("test.txt")) {

            writer.write(textToWrite);
            writer.append("\n");
            writer.write(textToWrite);

//            out.write(bytes);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void writeToConsole(String text) {}

}
