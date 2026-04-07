package com.alexnta.curriculumplanner.controller;

import com.alexnta.curriculumplanner.service.SubjectService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "SubjectListServlet", urlPatterns = {"/subjects"})
public class SubjectListServlet extends HttpServlet {

    private final SubjectService subjectService = new SubjectService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("subjects", subjectService.getAllSubjects());
        request.getRequestDispatcher("/WEB-INF/views/subject-list.jsp").forward(request, response);
    }
}
