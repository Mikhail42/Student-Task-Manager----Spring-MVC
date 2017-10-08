package com.ionkin;

import com.ionkin.beans.Student;
import com.ionkin.beans.StudentTask;
import com.ionkin.beans.StudentTaskView;
import com.ionkin.beans.Task;
import com.ionkin.dao.StudentDao;
import com.ionkin.dao.StudentTaskDao;
import com.ionkin.dao.TaskDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@SessionAttributes
public class HelloWorldController {

    private static final Logger logger = LoggerFactory.getLogger(HelloWorldController.class);

    @Autowired
    TaskDao taskDao;
    @Autowired
    StudentDao studentDao;
    @Autowired
    StudentTaskDao studentTaskDao;

    private Student studentToAdd = new Student();
    private StudentTask studentTask = new StudentTask();
    private String errorMessage = new String();

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String doGet(ModelMap model) throws SQLException {
        logger.info("doGet");

        model.addAttribute("studentToAdd", studentToAdd);
        model.addAttribute("studentTask", studentTask);
        model.addAttribute("errorMessage", errorMessage);

        List<Student> studentList = studentDao.getAll();
        List<StudentTaskView> studentTaskViewList =
                studentTaskDao.createStudentTaskViewList(studentList);
        Collections.sort(studentTaskViewList);
        model.addAttribute("studentTaskViews", studentTaskViewList);

        List<Task> taskList = taskDao.getAll();
        model.addAttribute("taskList", taskList);

        return "HelloWorldPage";
    }

    @RequestMapping(path = "/create/student", method = RequestMethod.POST)
    public String createStudent(@ModelAttribute("student") Student student,
                                BindingResult result, ModelMap model) throws SQLException {
        logger.info("createStudent with studentToAdd: {}", student);

        student.setFirstName(toUtf8(student.getFirstName()));
        student.setLastName(toUtf8(student.getLastName()));
        logger.info("studentToAdd: {}", student);

        errorMessage = "";
        boolean isEmpty = student.getFirstName().trim().isEmpty() || student.getLastName().trim().isEmpty();
        if (!result.hasErrors()) {
            if (isEmpty) {
                studentDao.create(student);
            }
        } else {
            errorMessage = result.getFieldError().getField();
        }

        return "redirect:/";
    }

    @RequestMapping(path = "/update/studentTask", method = RequestMethod.POST)
    public String updateStudentTask(@ModelAttribute("studentTask") StudentTask studentTask,
                                    BindingResult result, ModelMap model) throws SQLException {
        logger.info("updateStudentTask with studentTask: {}", studentTask);

        errorMessage = "";
        if (!result.hasErrors()) {
            Integer taskId = studentTask.getTaskId();
            Optional<StudentTask> studentTaskOpt = studentTaskDao.getAll().stream()
                    .filter(st -> st.getTaskId().equals(taskId))
                    .findFirst();

            if (studentTaskOpt.isPresent()) {
                errorMessage = "Данная задача уже выбрана. Пожалуйста, выберите другую задачу";
            } else {
                studentTaskDao.addOrUpdate(studentTask);
            }
        } else {
            errorMessage = "Ошибка: " + result.getFieldError().getField();
        }

        return "redirect:/";
    }

    public static String toUtf8(String s) {
        return new String(s.getBytes(StandardCharsets.UTF_8));
    }
}
