package id.co.asyst.wfm.attendance.service;

import id.co.asyst.wfm.attendance.model.Employee;
import id.co.asyst.wfm.attendance.repository.EmployeeRepository;
import id.co.asyst.wfm.core.service.BaseServiceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@Transactional
public class EmployeeServiceImpl extends BaseServiceManager<Employee,String> implements EmployeeService<Employee,String>
{

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Collection<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee findById(String id) {
        return employeeRepository.findById(id).get();
    }

    @Override
    public Employee saveOrUpdate(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public void deleteById(String id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public void delete(Employee employee) {
        employeeRepository.delete(employee);
    }
}
