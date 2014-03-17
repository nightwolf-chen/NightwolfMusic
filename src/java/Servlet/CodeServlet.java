//package Servlet;
//
//
//import java.awt.Color;  
//import java.awt.Font;  
//import java.awt.Graphics;  
//import java.awt.image.BufferedImage;  
//import java.io.IOException;  
//import java.util.Random;  
//import javax.servlet.ServletException;  
//import javax.servlet.http.HttpServlet;  
//import javax.servlet.http.HttpServletRequest;  
//import javax.servlet.http.HttpServletResponse;  
////import javax.servlet.http.HttpSession;  
//import com.sun.image.codec.jpeg.JPEGCodec;  
//import com.sun.image.codec.jpeg.JPEGImageEncoder;  
///**
// * 
// * @author nightwolf
// */
//@SuppressWarnings("serial")  
//public class CodeServlet extends HttpServlet {  
//    /** 
//     * 产生验证码图片 
//     * @param request
//     * @param response 
//     * @throws ServletException
//     * @throws IOException  
//     */  
//    @Override
//    public void doGet(HttpServletRequest request, HttpServletResponse response)  
//            throws ServletException, IOException {  
//        response.setContentType("image/jpeg");  
//        response.setHeader("Cache-Control", "no-cache");  
//        response.setHeader("Pragma", "No-cache");  
//        response.setDateHeader("Expires", 0L);  
//        //HttpSession session = request.getSession(true);  
//        int width = 80;  
//        int height = 30;  
//        BufferedImage image = new BufferedImage(width, height, 1);  
//        Graphics g = image.getGraphics();  
//        Random random = new Random();  
//        g.setColor(getRandColor(200, 250));  
//        g.fillRect(0, 0, width, height);  
//        g.setFont(new Font("Arial", 0, 25));  
//        g.setColor(getRandColor(160, 200));  
//        for (int i = 0; i < 155; i++) {  
//            int x = random.nextInt(width + 100);  
//            int y = random.nextInt(height + 100);  
//            int xl = random.nextInt(10);  
//            int yl = random.nextInt(12);  
//            g.drawOval(x, y, x + xl, y + yl);  
//        }  
//        String code = request.getParameter("code");  
//        //System.out.println(code);  
//        String sRand=code;  
//        for (int i = 0; i < sRand.length(); i++) {  
//            //String rand = getRandChar(random.nextInt(36));  
//            String rand = sRand.substring(i, i+1);  
//            //sRand = sRand + rand;  
//            g.setColor(new Color(20 + random.nextInt(110), 20 + random  
//                    .nextInt(110), 20 + random.nextInt(110)));  
//            g.drawString(rand, 14 * i + 5, 25);  
//        }  
//        //session.setAttribute("rand", sRand);  
//        //request.setAttribute("rand", sRand);  
//        g.dispose();  
//        javax.servlet.ServletOutputStream imageOut = response.getOutputStream();  
//        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(imageOut);  
//        encoder.encode(image);  
//    }  
//    private Color getRandColor(int fc, int bc) {  
//        Random random = new Random();  
//        if (fc > 255)  
//            fc = 255;  
//        if (bc > 255)  
//            bc = 255;  
//        int r = fc + random.nextInt(bc - fc);  
//        int g = fc + random.nextInt(bc - fc);  
//        int b = fc + random.nextInt(bc - fc);  
//        return new Color(r, g, b);  
//    }  
//  
///*  private String getRandChar(int randNumber) { 
//        return CHARARRAY[randNumber]; 
//    }*/  
//    //private static final String CONTENT_TYPE = "image/jpeg";  
///*  private static final String CHARARRAY[] = { "0", "1", "2", "3", "4", "5", 
//            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", 
//            "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", 
//            "w", "x", "y", "z" };*/  
//    }  