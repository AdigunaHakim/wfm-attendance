package id.co.asyst.wfm.attendance.repository;

import id.co.asyst.wfm.attendance.model.Attendance;
import id.co.asyst.wfm.core.repository.BaseJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface AttendanceRepository extends BaseJpaRepository<Attendance, Long>
{
    @Query("SELECT p FROM Attendance p WHERE LOWER(p.employee.employeeId) = LOWER(:employeeId) and p.workingDate = current_date()")
    public Attendance findTodayAttendanceByEmployee(@Param("employeeId") String employeeId);

    @Query("SELECT p FROM Attendance p WHERE LOWER(p.employee.employeeId) = LOWER(:employeeId) and p.workingDate = :workingDate")
    public Attendance findAttendanceByEmployee(@Param("workingDate") Date workingDate, @Param("employeeId") String employeeId);
}
