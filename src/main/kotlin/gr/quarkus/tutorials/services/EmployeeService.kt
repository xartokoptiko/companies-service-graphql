package gr.quarkus.tutorials.services

import gr.quarkus.tutorials.repositories.EmployeeRepository
import javax.enterprise.context.ApplicationScoped

import gr.quarkus.tutorials.entities.Employee
import io.quarkus.logging.Log
import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.coroutines.awaitSuspending
import io.vertx.core.json.JsonObject
import java.time.LocalDateTime
import javax.ws.rs.core.Response

@ApplicationScoped
class EmployeeService(val employeeRepository: EmployeeRepository) {

    fun getEmployees(page: Int, size: Int): Uni<MutableList<Employee>> =
        employeeRepository.getPage(page, size)


    fun getEmployee(id: Long): Uni<Employee>? =
        employeeRepository.findById(id)

    fun createEmployee(employee: Employee): Uni<Employee> =
        employeeRepository.persistAndFlush(employee)

    fun delete(id: Long): Uni<Boolean> =
        employeeRepository.deleteById(id)
            .call { _-> employeeRepository.flush() }
}