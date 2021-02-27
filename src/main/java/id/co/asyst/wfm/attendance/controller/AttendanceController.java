package id.co.asyst.wfm.attendance.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.co.asyst.wfm.attendance.model.Attendance;
import id.co.asyst.wfm.attendance.model.Employee;
import id.co.asyst.wfm.attendance.service.AttendanceService;
import id.co.asyst.wfm.attendance.service.EmployeeService;
import id.co.asyst.wfm.core.dto.*;
import id.co.asyst.wfm.core.exception.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Calendar;

@RestController
@RequestMapping("/attendance")
public class AttendanceController
{
    @Autowired
    EmployeeService employeeService;

    @Autowired
    AttendanceService attendanceService;

    @PostMapping("/list")
    public Response listEmployees(@Valid @RequestBody Request request) {
        // Parse Request
        Identity identity = request.getIdentity();
        Paging paging = request.getPaging();
        Parameter parameter = request.getParameter();

        // Process
        Object result = employeeService.findAll();

        // Build Response
        Response response = new Response();

        response.setIdentity(identity);
        response.setPaging(paging);
        response.setResult(result);

        Status status = new Status();
        status.setResponsecode(Status.SUCCESS_CODE);
        status.setResponsedesc(Status.SUCCESS_DESC);
        response.setStatus(status);

        return response;
    }

    @PostMapping("/start")
    public Response startTime(@Valid @RequestBody Request request) {
        // Parse Request
        Identity identity = request.getIdentity();
        Paging paging = request.getPaging();
        Parameter parameter = request.getParameter();

        // Process
        ObjectMapper mapper = new ObjectMapper();
        Attendance attendance = mapper.convertValue(parameter.getData(), Attendance.class);
        Attendance attendanceIn = null;
        if (attendance.getWorkingDate() == null)
            attendance.setWorkingDate(Calendar.getInstance().getTime());

        attendanceIn = attendanceService.findAttendanceByEmployee(attendance.getWorkingDate(), attendance.getEmployee().getEmployeeId());
        if (attendanceIn == null) {
            attendanceIn = new Attendance();
            Employee employee = (Employee) employeeService.findById(attendance.getEmployee().getEmployeeId());
            attendanceIn.setEmployee(employee);
            attendanceIn.setCreatedBy("Admin");
            attendanceIn.setCreatedDateTime(Calendar.getInstance().getTime());
        } else {
            attendanceIn.setUpdatedBy("Admin");
            attendanceIn.setUpdatedDateTime(Calendar.getInstance().getTime());
        }
        attendanceIn.setWorkingDate(attendance.getWorkingDate());
        attendanceIn.setStartTime(attendance.getStartTime());
        Object result = attendanceService.saveOrUpdate(attendanceIn);

        // Build Response
        Response response = new Response();

        response.setIdentity(identity);
        response.setPaging(paging);
        response.setResult(result);

        Status status = new Status();
        status.setResponsecode(Status.SUCCESS_CODE);
        status.setResponsedesc(Status.SUCCESS_DESC);
        response.setStatus(status);

        return response;
    }

    @PostMapping("/stop")
    public Response stopTime(@Valid @RequestBody Request request) {
        // Parse Request
        Identity identity = request.getIdentity();
        Paging paging = request.getPaging();
        Parameter parameter = request.getParameter();

        // Process
        ObjectMapper mapper = new ObjectMapper();
        Attendance attendance = mapper.convertValue(parameter.getData(), Attendance.class);
        Attendance attendanceOut = null;
        if (attendance.getWorkingDate() == null)
            attendance.setWorkingDate(Calendar.getInstance().getTime());

        attendanceOut = attendanceService.findAttendanceByEmployee(attendance.getWorkingDate(), attendance.getEmployee().getEmployeeId());
        if (attendanceOut == null) {
            throw new ApplicationException("Please Start before End");
        }
        attendanceOut.setEndTime(attendance.getEndTime());
        attendanceOut.setUpdatedBy("Admin");
        attendanceOut.setUpdatedDateTime(Calendar.getInstance().getTime());
        Object result = attendanceService.saveOrUpdate(attendanceOut);

        // Build Response
        Response response = new Response();

        response.setIdentity(identity);
        response.setPaging(paging);
        response.setResult(result);

        Status status = new Status();
        status.setResponsecode(Status.SUCCESS_CODE);
        status.setResponsedesc(Status.SUCCESS_DESC);
        response.setStatus(status);

        return response;
    }

}
