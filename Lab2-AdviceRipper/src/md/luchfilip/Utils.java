package md.luchfilip;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by luchfilip on 11/15/15.
 *
 */
public class Utils {


    public static String findAdviceByPattern(String htmlCode) {
        String result = "";
        String pattern = "<p id=\"advice\">";

        if (htmlCode.contains(pattern)) {
            int patternIndex = htmlCode.indexOf(pattern) + 15;
//
            result = htmlCode.substring(patternIndex, htmlCode.indexOf("</p>"));
//            System.out.println(result);

            // remove formatted text tags if exists
            while (result.contains("<")) {
                String tag = result.substring(result.indexOf("<"), result.indexOf(">")+1);
                result = result.replace(tag, "");
            }
        }
            result = result.replaceAll("&nbsp;", " ");
            result = result.replaceAll("&laquo;", "«");
            result = result.replaceAll("&raquo;", "»");

        return result;
    }


    public static void getAdvice(int adviceNumber) {
        String extractedAdvice = "";
        try {
            String hostname = "fucking-great-advice.ru";
            int port = 80;
            InetAddress addr = InetAddress.getByName(hostname);
            Socket socket = new Socket(addr, port);
            socket.setSoTimeout(4000);

            PrintWriter pw = new PrintWriter(socket.getOutputStream());
            pw.print("GET /advice/" + String.valueOf(adviceNumber) + " HTTP/1.1\r\n");
            pw.print("Host: fucking-great-advice.ru\r\n\r\n");
            pw.flush();

            // Get response
            BufferedReader rd = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line;

            while ((line = rd.readLine()) != null) {
                if (!Utils.findAdviceByPattern(line).equals("")) {
                    extractedAdvice = Utils.findAdviceByPattern(line);
                    break;
                }
            }
            rd.close();
        }
        catch (Exception e) {
//            e.printStackTrace();
        }
        if (extractedAdvice.equals("")) {
            getAdvice(adviceNumber);
            Main.retryCount++;
        } else {
            System.out.println("Advice #" + adviceNumber + ": " + extractedAdvice);
        }
    }
}
