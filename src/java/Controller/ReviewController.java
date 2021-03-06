/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Modell.Game;
import Modell.Review;
import Modell.User;
import Service.ReviewService;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Win10
 */
@WebServlet(name = "ReviewController", urlPatterns = {"/ReviewController"})
public class ReviewController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
            if (request.getParameter("task").equals("getScoreDistribution")) {
                JSONObject result = new JSONObject();
                try {
                    JSONObject scoreDistribution = ReviewService.getScoreDistribution();
                    result.put("result", scoreDistribution);
                } catch (Exception e) {
                    System.out.println("Hiba a JSON adatok beolvasásakor!");
                }
                out.println(result);
            }
            if (request.getParameter("task").equals("getReviewsOverTime")) {
                JSONObject result = new JSONObject();
                try {
                    JSONObject reviewsOverTime = ReviewService.getReviewsOverTime();
                    result.put("result", reviewsOverTime);
                } catch (Exception e) {
                    System.out.println("Hiba a JSON adatok beolvasásakor!");
                }
                out.println(result);
            }
            if (request.getParameter("task").equals("reviewListbyUser")) {
                JSONObject result = new JSONObject();
                if (!request.getParameter("id").isEmpty()) {
                    try {
                        Integer id = Integer.parseInt(request.getParameter("id"));
                        JSONArray reviewListbyUser = ReviewService.getReviewListbyUser(id);
                        result.put("result", reviewListbyUser);
                    } catch (Exception e) {
                        System.out.println("Hiba a JSON adatok beolvasásakor!");
                    }
                    out.println(result);
                }
            }

            if (request.getParameter("task").equals("reviewListbyGame")) {
                JSONObject result = new JSONObject();
                if (!request.getParameter("id").isEmpty()) {
                    try {
                        Integer id = Integer.parseInt(request.getParameter("id"));
                        JSONArray reviewListbyGame = ReviewService.getReviewListbyGame(id);
                        result.put("result", reviewListbyGame);
                    } catch (Exception e) {
                        System.out.println("Hiba a JSON adatok beolvasásakor!");
                    }
                    out.println(result);
                }
            }

            if (request.getParameter("task").equals("addReview")) {
                JSONObject result = new JSONObject();
                if (!request.getParameter("userId").isEmpty()
                        && !request.getParameter("gameId").isEmpty()
                        && !request.getParameter("score").isEmpty()
                        && !request.getParameter("comment").isEmpty()) {
                    try {
                        Integer userId = Integer.parseInt(request.getParameter("userId"));
                        Integer gameId = Integer.parseInt(request.getParameter("gameId"));
                        Integer score = Integer.parseInt(request.getParameter("score"));
                        String comment = request.getParameter("comment");

                        Review review = new Review(new User(userId), new Game(gameId), score, comment);
                        Boolean serviceResult = ReviewService.addReview(review);
                        result.put("result", serviceResult);

                    } catch (Exception e) {
                        System.out.println("Hiba a JSON adatok beolvasásakor!");
                    }

                } else {
                    result.put("result", "A mezők nincsenek megfelelően kitöltve");
                }
                out.println(result);
            }

            if (request.getParameter("task").equals("updateReview")) {
                JSONObject result = new JSONObject();
                if (!request.getParameter("id").isEmpty()
                        && !request.getParameter("score").isEmpty()
                        && !request.getParameter("comment").isEmpty()) {
                    try {
                        Integer id = Integer.parseInt(request.getParameter("id"));
                        Integer score = Integer.parseInt(request.getParameter("score"));
                        String comment = request.getParameter("comment");

                        Review review = new Review(id, score, comment);
                        Boolean serviceResult = ReviewService.updateReview(review);
                        result.put("result", serviceResult);

                    } catch (Exception e) {
                        System.out.println("Hiba a JSON adatok beolvasásakor!");
                    }

                } else {
                    result.put("result", "A mezők nincsenek megfelelően kitöltve");
                }
                out.println(result);
            }

            if (request.getParameter("task").equals("getReview")) {
                JSONObject result = new JSONObject();
                if (!request.getParameter("id").isEmpty()) {
                    try {
                        Integer id = Integer.parseInt(request.getParameter("id"));

                        Review serviceResult = ReviewService.getReview(id);
                        result.put("result", serviceResult);

                    } catch (Exception e) {
                        System.out.println("Hiba a JSON adatok beolvasásakor!");
                    }

                } else {
                    result.put("result", "A mezők nincsenek megfelelően kitöltve");
                }
                out.println(result);
            }

        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
