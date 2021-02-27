package id.co.asyst.wfm.attendance.service;


import id.co.asyst.wfm.attendance.model.Attendance;
import id.co.asyst.wfm.core.service.ServiceManager;

import java.util.Date;

public interface AttendanceService<T,ID> extends ServiceManager<T,ID>
{

    public Attendance findTodayAttendanceByEmployee(String employeeId);
    public Attendance findAttendanceByEmployee(Date workingDate, String employeeId);

}
